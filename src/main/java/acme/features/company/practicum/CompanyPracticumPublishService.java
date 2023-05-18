
package acme.features.company.practicum;

import java.util.Collection;

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
public class CompanyPracticumPublishService extends AbstractService<Company, Practicum> {

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
		boolean status;
		Practicum practicum;
		Principal principal;
		int practicumId;
		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		principal = super.getRequest().getPrincipal();
		status = practicum.getCompany().getId() == principal.getActiveRoleId();

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
	public void bind(final Practicum object) {
		assert object != null;

		//		int courseId;
		//		Course course;
		//
		//		courseId = super.getRequest().getData("course", int.class);
		//		course = this.repository.findCourseById(courseId);
		//
		//		super.bind(object, "code", "title", "abstractPracticum", "someGoals", "draftMode");
		object.setDraftMode(false);
		//		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		final Collection<SessionPracticum> sessions = this.repository.findSessionPracticumByPracticumId(object.getId());
		for (final SessionPracticum s : sessions)
			this.repository.save(s);
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "title", "abstractPracticum", "someGoals");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}
