
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.activities.ActivityType;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityShowService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int activityId;
		Enrolment enrolment;

		activityId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findOneEnrolmentByActivityId(activityId);
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Activity activity;
		int id;

		id = super.getRequest().getData("id", int.class);
		activity = this.repository.findActivityById(id);

		super.getBuffer().setData(activity);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		final Enrolment enrolment = this.repository.findOneEnrolmentByActivityId(super.getRequest().getData("id", int.class));
		choices = SelectChoices.from(ActivityType.class, object.getActivityType());

		tuple = super.unbind(object, "title", "abstractResumen", "activityType", "timePeriod", "timePeriodInitial", "timePeriodFinal", "link");
		tuple.put("draftMode", enrolment.isDraftMode());
		tuple.put("activityType", choices.getSelected().getKey());
		tuple.put("activities", choices);

		super.getResponse().setData(tuple);
	}

}
