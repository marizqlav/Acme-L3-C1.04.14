
package acme.features.company.sessionPracticum;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
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
public class CompanySessionPracticumCreateAddendumService extends AbstractService<Company, SessionPracticum> {

	@Autowired
	protected CompanySessionPracticumRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("practicumId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status = true;

		final Practicum practicum = this.repository.findPracticumById(super.getRequest().getData("practicumId", int.class));
		status = practicum != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final SessionPracticum sessionPracticum = new SessionPracticum();

		sessionPracticum.setAddendum(true);

		super.getBuffer().setData(sessionPracticum);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;

		super.bind(object, "title", "abstractSessionPracticum", "startDate", "finishDate", "link");
		final Practicum practicum = this.repository.findPracticumById(super.getRequest().getData("practicumId", int.class));

		object.setPracticum(practicum);

	}

	@Override
	public void validate(final SessionPracticum object) {
		assert object != null;

		final Date date;

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

		final Practicum practicum = this.repository.findPracticumById(super.getRequest().getData("practicumId", int.class));
		if (this.repository.findAddendumSessionPracticumByPracticumId(practicum.getId()) != null && this.repository.findAddendumSessionPracticumByPracticumId(practicum.getId()).size() != 0)
			super.state(false, "title", "company.practicum.error.practicum.exist");

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "company.session-practicum.validation.confirmation");
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
		tuple.put("addendum", object.isAddendum());
		final boolean draftMode = this.repository.findPracticumById(super.getRequest().getData("practicumId", int.class)).isDraftMode();
		tuple.put("confirmation", false);
		tuple.put("draftMode", draftMode);
		tuple.put("practicumId", super.getRequest().getData("practicumId", int.class));

		super.getResponse().setData(tuple);

	}

	public static Date oneWeekLong(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		return calendar.getTime();
	}

}
