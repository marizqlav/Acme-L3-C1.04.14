
package acme.features.assistant.sessionTutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.SessionTutorial;
import acme.entities.tutorial.SessionType;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionTutorialUpdateService extends AbstractService<Assistant, SessionTutorial> {

	// Internal State ------------------------------------------
	@Autowired
	protected AssistantSessionTutorialRepository	repository;

	@Autowired
	protected LecturerCourseRepository				courseRepo;

	//AbstractServiceInterface -------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		SessionTutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void validate(final SessionTutorial object) {
		assert object != null;

	}

	@Override
	public void perform(final SessionTutorial object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void bind(final SessionTutorial object) {
		assert object != null;

		super.bind(object, "title", "description", "sessionType", "startDate", "endDate", "link", "draftMode");
	}

	@Override
	public void unbind(final SessionTutorial object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(SessionType.class, object.getSessionType());
		tuple = super.unbind(object, "title", "description", "sessionType", "startDate", "endDate", "link", "draftMode");
		tuple.put("sessionTypes", choices);
		tuple.put("id", super.getRequest().getData("id", int.class));
		super.getResponse().setData(tuple);
	}
}
