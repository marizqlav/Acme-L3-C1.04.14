/*
 * AuthenticatedAnnouncementController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.company.practicum;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.practicum.Practicum;
import acme.features.codeGeneration.CodeGenerator;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumCreateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository	repository;

	@Autowired
	protected CodeGenerator					codeGenerator;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		Company company;

		company = this.repository.findCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Practicum();
		object.setDraftMode(true);
		object.setCompany(company);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		int courseId;
		Course course;
		Company company;
		company = this.repository.findCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		super.bind(object, "title", "abstractPracticum", "someGoals");
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		final String code = this.codeGenerator.newCode(Practicum.class.getSimpleName());
		object.setCode(code);
		object.setCompany(company);
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
		Integer courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		if (!(course == null))
			if (course.getDraftMode())
				super.state(false, "course", "company.practicum.error.course.not.public");
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		final Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;
		final Iterator<Course> iterator;

		courses = this.repository.findCoursesPublics();

		iterator = courses.iterator();
		choices = new SelectChoices();
		choices.add("0", "---", object.getCourse() == null);
		while (iterator.hasNext()) {
			Course choice;
			choice = iterator.next();
			choices.add(String.valueOf(choice.getId()), choice.getCode() + ": " + choice.getTitle(), choice.equals(object.getCourse()));
		}

		tuple = super.unbind(object, "title", "abstractPracticum", "someGoals");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}
