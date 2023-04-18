/*
 * EmployerDutyCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.activities.ActivityType;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		Boolean status;
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
		status = enrolment != null && enrolment.isDraftMode() && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		object = new Activity();
		object.setEnrolment(enrolment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "abstractResumen", "activityType", "timePeriodInitial", "timePeriodFinal", "link");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		if (super.getBuffer().getErrors().hasErrors("timePeriodInitial"))
			super.state(false, "timePeriodInitial", "student.activity.form.error.time.period.initial");
		else if (super.getBuffer().getErrors().hasErrors("timePeriodFinal"))
			super.state(false, "timePeriodFinal", "student.activity.form.error.time.period.final");
		else if (MomentHelper.isAfter(object.getTimePeriodInitial(), object.getTimePeriodFinal())) {
			super.state(false, "timePeriodInitial", "student.activity.form.error.start.is.after.end");
			super.state(false, "timePeriodFinal", "student.activity.form.error.end.is.before.start");
		}

	}

	@Override
	public void perform(final Activity object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(ActivityType.class, object.getActivityType());

		tuple = super.unbind(object, "title", "abstractResumen", "timePeriodInitial", "timePeriodFinal", "link");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("activityType", choices.getSelected().getKey());
		tuple.put("activities", choices);

		super.getResponse().setData(tuple);
	}

}
