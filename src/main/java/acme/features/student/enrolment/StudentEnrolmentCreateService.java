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

package acme.features.student.enrolment;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int studentId;
		Student student;

		studentId = super.getRequest().getPrincipal().getActiveRoleId();
		student = this.repository.findStudentById(studentId);
		object = new Enrolment();
		object.setDraftMode(true);
		object.setStudent(student);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;
		Integer courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);
		super.bind(object, "motivation", "someGoals");
		object.setCourse(course);
		object.setCode(this.newCode(this.repository.findFirstByOrderByCodeDesc().getCode()));

	}

	public String newCode(final String lastCode) {

		String prefijo = lastCode.substring(0, 3);
		final int numeroActual = Integer.parseInt(lastCode.substring(3));
		int nuevoNumero = numeroActual + 1;
		if (nuevoNumero > 999) {
			int indiceLetra = prefijo.charAt(2) - 'A';
			if (indiceLetra == 25)
				throw new RuntimeException("Se alcanzó el límite de códigos posibles");
			indiceLetra++;
			final char nuevaLetra = (char) ('A' + indiceLetra);
			prefijo = prefijo.substring(0, 2) + nuevaLetra;
			nuevoNumero = 0;
		}
		final String nuevoCodigo = prefijo + String.format("%03d", nuevoNumero);
		return nuevoCodigo;
	}
	@Override
	public void validate(final Enrolment object) {
		assert object != null;
		int studentId;
		studentId = super.getRequest().getPrincipal().getActiveRoleId();
		Integer courseId;
		Course course;
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);
		if (!(course == null)) {
			final Enrolment enrolmented = this.repository.findStudentCourse(studentId, course.getId());
			if (enrolmented != null)
				super.state(false, "course", "student.enrolment.error.enrolment.exist");
		}

	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;
		Iterator<Course> iterator;

		courses = this.repository.findCoursesPublics();

		iterator = courses.iterator();
		choices = new SelectChoices();
		choices.add("0", "---", object.getCourse() == null);
		while (iterator.hasNext()) {
			Course choice;
			choice = iterator.next();
			choices.add(String.valueOf(choice.getId()), choice.getCode() + ": " + choice.getTitle(), choice.equals(object.getCourse()));
		}

		tuple = super.unbind(object, "motivation", "someGoals");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);
	}

}
