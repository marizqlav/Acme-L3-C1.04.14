/*
 * StudentEnrolmentShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentShowService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		Enrolment enrolment;
		Student student;

		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(student);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;
		Double workTime;
		int id;

		id = super.getRequest().getData("id", int.class);

		final Collection<Activity> activities = this.repository.findManyActivitiesByEnrolmentId(id);
		workTime = activities.stream().mapToDouble(x -> x.getTimePeriod()).sum();

		tuple = super.unbind(object, "code", "motivation", "someGoals", "draftMode");
		tuple.put("workTime", workTime != null ? workTime : 0.00);
		super.getResponse().setData(tuple);
	}

}
