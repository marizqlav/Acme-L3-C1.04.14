
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

	@Autowired
	StudentActivityRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		status = enrolment != null && !enrolment.isDraftMode() && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Collection<Activity> activities = this.repository.findAllActivitiesFromEnrolmentId(super.getRequest().getData("masterId", int.class));

		super.getBuffer().setData(activities);
	}

	@Override

	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractResumen", "activityType", "timePeriod");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Activity> objects) {
		assert objects != null;

		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);

		super.getResponse().setGlobal("draftMode", enrolment.isDraftMode());
		super.getResponse().setGlobal("masterId", super.getRequest().getData("masterId", int.class));
	}
}
