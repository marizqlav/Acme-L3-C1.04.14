
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
public class CompanySessionPracticumCreateService extends AbstractService<Company, SessionPracticum> {

	@Autowired
	protected CompanySessionPracticumRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final SessionPracticum object;
		object = new SessionPracticum();
		object.setDraftMode(true);
		object.setAddendum(false);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;

		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		super.bind(object, "title", "abstractSessionPracticum", "startDate", "finishDate", "link");
		object.setPracticum(practicum);

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
		tuple = super.unbind(object, "title", "abstractSessionPracticum", "startDate", "finishDate", "link", "draftMode", "addendum");
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
