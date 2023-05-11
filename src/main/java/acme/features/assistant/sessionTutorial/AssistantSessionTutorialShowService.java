
package acme.features.assistant.sessionTutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.SessionTutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionTutorialShowService extends AbstractService<Assistant, SessionTutorial> {

	@Autowired
	protected AssistantSessionTutorialRepository repo;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
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
		object = this.repo.findSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final SessionTutorial object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "description", "sessionType", "startDate", "endDate", "link");

		super.getResponse().setData(tuple);
	}

}
