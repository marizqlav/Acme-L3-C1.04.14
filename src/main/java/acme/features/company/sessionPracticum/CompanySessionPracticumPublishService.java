
package acme.features.company.sessionPracticum;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumPublishService extends AbstractService<Company, SessionPracticum> {

	@Autowired
	protected CompanySessionPracticumRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		SessionPracticum object;
		Principal principal;
		int sessionPracticumId;

		sessionPracticumId = super.getRequest().getData("id", int.class);
		object = this.repository.findSessionPracticumById(sessionPracticumId);
		principal = super.getRequest().getPrincipal();

		status = object.getPracticum().getCompany().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSessionPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;
		//		int practicumId;
		//		Practicum practicum;
		//
		//		practicumId = super.getRequest().getData("practicum", int.class);
		//		practicum = this.repository.findPracticumById(practicumId);
		super.bind(object, "title", "abstractSessionPracticum", "startDate", "finishDate", "link");
		//		object.setPracticum(practicum);
	}

	@Override
	public void validate(final SessionPracticum object) {
		assert object != null;

		//Date Validations

		Date date;

		if (!super.getBuffer().getErrors().hasErrors("finishDate"))
			super.state(object.getStartDate().before(object.getFinishDate()), "finishDate", "company.session-practicum.form.error.finishAfterStart");

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			date = CompanySessionPracticumCreateService.oneWeekLong(Date.from(Instant.now()));
			super.state(object.getStartDate().equals(date) || object.getStartDate().after(date), "startDate", "company.session-practicum.form.error.oneWeekAhead");
		}

		if (!super.getBuffer().getErrors().hasErrors("finishDate") && !super.getBuffer().getErrors().hasErrors("startDate")) {
			Date maximumPeriod;
			maximumPeriod = MomentHelper.deltaFromMoment(object.getStartDate(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getFinishDate(), maximumPeriod) && object.getFinishDate().after(object.getStartDate()), "finishDate", "company.session-practicum.form.error.finishDate");
		}

		//Practicum Validation
		final Collection<Practicum> practica;
		final SelectChoices choices;
		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();

		practica = this.repository.findManyPrivatePracticumByCompanyId(companyId);
		choices = SelectChoices.from(practica, "code", object.getPracticum());

		final int selectedId = Integer.parseInt(choices.getSelected().getKey());
		final Practicum selectedPracticum = this.repository.findPracticumById(selectedId);

		final boolean valid = selectedPracticum.getDraftMode();
		super.state(valid, "practicum", "company.session.validation.practicum.error.Published");
	}

	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;
		final Collection<Practicum> practica;
		final SelectChoices choices;
		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();

		practica = this.repository.findManyPrivatePracticumByCompanyId(companyId);
		choices = SelectChoices.from(practica, "code", object.getPracticum());
		Tuple tuple;
		tuple = super.unbind(object, "title", "abstractSessionPracticum", "startDate", "finishDate", "link", "draftMode", "addendum");
		tuple.put("practicum", choices.getSelected().getKey());
		tuple.put("practica", choices);
		super.getResponse().setData(tuple);
	}

}
