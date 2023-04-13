/*
 * AuthenticatedAnnouncementShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentCourseRepository repository;

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
		int id;

		id = super.getRequest().getData("id", int.class);

		status = true;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		final Enrolment enrolmented = this.repository.findStudentCourse(super.getRequest().getPrincipal().getActiveRoleId(), object.getId());
		tuple = super.unbind(object, "id", "code", "title", "resumen", "retailPrice", "link");
		tuple.put("lecturerusername", object.getLecturer().getUserAccount().getUsername());
		tuple.put("lectureralmamater", object.getLecturer().getAlmaMater());
		tuple.put("lecturerresume", object.getLecturer().getResume());
		tuple.put("lecturerqualifications", object.getLecturer().getQualifications());
		tuple.put("lecturerlink", object.getLecturer().getLink());

		if (enrolmented != null)
			tuple.put("enrolment", "yes");
		else
			tuple.put("enrolment", "no");

		super.getResponse().setData(tuple);

	}

}
