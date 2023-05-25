
package acme.features.company.sessionPracticum;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumUpdateService extends AbstractService<Company, SessionPracticum> {

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
		int sessionPracticumId;
		Practicum practicum;
		SessionPracticum sessionPracticum;
		sessionPracticumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumBySessionPracticumId(sessionPracticumId);
		sessionPracticum = this.repository.findSessionPracticumById(sessionPracticumId);
		status = super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && sessionPracticum != null && practicum.isDraftMode() && sessionPracticum.isDraftModeSession();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final SessionPracticum object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSessionPracticumById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;

		super.bind(object, "title", "abstractSessionPracticum", "startDate", "finishDate", "link");
	}

	@Override
	public void validate(final SessionPracticum object) {
		assert object != null;
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

		if (object.getTitle() == null || object.getTitle().isEmpty() || object.getTitle() == "")
			super.state(false, "title", "title.not.null");
		if (object.getAbstractSessionPracticum() == null || object.getAbstractSessionPracticum().isEmpty() || object.getAbstractSessionPracticum() == "")
			super.state(false, "abstractSessionPracticum", "abstractSessionPracticum.not.null");
		if (object.getStartDate() == null)
			super.state(false, "startDate", "startDate.not.null");
		if (object.getFinishDate() == null)
			super.state(false, "finishDate", "finishDate.not.null");

	}

	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractSessionPracticum", "startDate", "finishDate", "link");
		tuple.put("draftMode", object.getPracticum().isDraftMode());
		tuple.put("draftModeSession", object.isDraftModeSession());
		tuple.put("addendum", object.isAddendum());
		super.getResponse().setData(tuple);
	}

}
