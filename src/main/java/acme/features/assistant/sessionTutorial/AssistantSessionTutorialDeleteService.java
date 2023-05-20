
package acme.features.assistant.sessionTutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.SessionTutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionTutorialDeleteService extends AbstractService<Assistant, SessionTutorial> {

	// Internal State ------------------------------------------
	@Autowired
	protected AssistantSessionTutorialRepository repository;

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
		SessionTutorial session;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findSessionById(id);
		status = session != null;

		super.getResponse().setAuthorised(status);
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
	public void bind(final SessionTutorial object) {
		assert object != null;

		super.bind(object, "title", "description", "sessionType", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final SessionTutorial object) {
		assert object != null;
	}

	@Override
	public void perform(final SessionTutorial object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final SessionTutorial object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "description", "sessionType", "startDate", "endDate", "link");

		super.getResponse().setData(tuple);
	}
}
