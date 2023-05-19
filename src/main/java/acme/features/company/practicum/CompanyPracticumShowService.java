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

package acme.features.company.practicum;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumShowService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		Practicum object;
		Principal principal;
		int practicumId;

		practicumId = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(practicumId);
		principal = super.getRequest().getPrincipal();

		status = object != null && object.getCompany().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		final Collection<Course> courses;
		final SelectChoices choices;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		final Collection<SessionPracticum> sessionPracticum;
		Date fechaInicial = null;
		Date fechaFinal = null;
		Double estimatedTimeMenos = null;
		Double estimatedTimeMas = null;

		sessionPracticum = this.repository.findSessionPracticumByPracticumId(object.getId());
		if (sessionPracticum != null && sessionPracticum.size() > 0) {
			fechaInicial = object.fechaInicial(sessionPracticum);
			fechaFinal = object.fechaFinal(sessionPracticum);
			estimatedTimeMenos = object.estimatedTimeMenos(sessionPracticum);
			estimatedTimeMas = object.estimatedTimeMas(sessionPracticum);
		}

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abstractPracticum", "someGoals", "draftMode");
		tuple.put("companyusername", object.getCompany().getUserAccount().getUsername());
		tuple.put("companyname", object.getCompany().getName());
		tuple.put("companyVATNumber", object.getCompany().getVATNumber());
		tuple.put("companysummary", object.getCompany().getSummary());
		tuple.put("companylink", object.getCompany().getLink());

		tuple.put("estimatedTimeMenos", estimatedTimeMenos);
		tuple.put("estimatedTimeMas", estimatedTimeMas);
		tuple.put("fechaInicial", fechaInicial);
		tuple.put("fechaFinal", fechaFinal);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		if (this.repository.findSessionPracticumByPracticumId(object.getId()).isEmpty())
			tuple.put("emptySesions", true);
		else
			tuple.put("emptySesions", false);

		super.getResponse().setData(tuple);
	}
}
