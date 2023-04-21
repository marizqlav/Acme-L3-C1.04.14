
package acme.features.assistant.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

	@Autowired
	protected AssistantTutorialRepository	repo;

	@Autowired
	protected LecturerCourseRepository		courseRepo;


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
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;

		choices = new SelectChoices();

		choices = SelectChoices.from(this.courseRepo.findAllCourses(), "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "description", "estimatedTime");
		tuple.put("courses", choices);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("draftMode", object.getDraftMode());
		super.getResponse().setData(tuple);
	}
}
