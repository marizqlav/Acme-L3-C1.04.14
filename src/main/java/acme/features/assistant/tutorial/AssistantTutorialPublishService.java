
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
public class AssistantTutorialPublishService extends AbstractService<Assistant, Tutorial> {

	// Internal State ------------------------------------------
	@Autowired
	protected AssistantTutorialRepository	repository;

	@Autowired
	protected LecturerCourseRepository		courseRepo;

	//AbstractServiceInterface -------------------------------


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
		Tutorial lecture;

		id = super.getRequest().getData("id", int.class);
		lecture = this.repository.findTutorialById(id);
		status = lecture != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;

		super.bind(object, "title", "description", "estimatedTime", "code");
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("estimatedTime"))
			super.state(object.getEstimatedTime() >= 0.01, "estimatedTime", "assistant.tutorial.form.error.estimatedTime");
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		object.setDraftMode(true);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;

		choices = new SelectChoices();

		choices = SelectChoices.from(this.courseRepo.findAllCourses(), "title", object.getCourse());

		tuple = super.unbind(object, "title", "description", "estimatedTime");
		tuple.put("courses", choices);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("assistant", this.repository.findAssistantById(super.getRequest().getPrincipal().getActiveRoleId()));
		super.getResponse().setData(tuple);
	}
}
