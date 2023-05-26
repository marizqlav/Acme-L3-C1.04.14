
package acme.features.assistant.sessionTutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.SessionTutorial;
import acme.entities.tutorial.SessionType;
import acme.framework.components.jsp.SelectChoices;
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
		boolean status;
		int sessionId;
		SessionTutorial session;
		Assistant assistant;

		sessionId = super.getRequest().getData("id", int.class);
		session = this.repo.findSessionById(sessionId);
		assistant = session == null ? null : session.getTutorial().getAssistant();
		status = session != null && super.getRequest().getPrincipal().hasRole(assistant);

		super.getResponse().setAuthorised(status);
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
		SelectChoices choices;

		choices = SelectChoices.from(SessionType.class, object.getSessionType());

		tuple = super.unbind(object, "title", "description", "sessionType", "startDate", "endDate", "link", "draftMode");

		tuple.put("sessionTypes", choices);

		super.getResponse().setData(tuple);
	}

}
