
package acme.features.assistant.sessionTutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.SessionTutorial;
import acme.entities.tutorial.SessionType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionTutorialCreateService extends AbstractService<Assistant, SessionTutorial> {

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
		final SessionTutorial object = new SessionTutorial();

		final int tutorialId = super.getRequest().getData("tutorialId", int.class);
		object.setDraftMode(true);
		object.setTutorial(this.repo.findTutorialById(tutorialId));
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionTutorial object) {

		assert object != null;
		final int tutorialId = super.getRequest().getData("tutorialId", int.class);
		object.setDraftMode(true);
		object.setTutorial(this.repo.findTutorialById(tutorialId));
		super.bind(object, "title", "description", "sessionType", "startDate", "endDate", "link");
	}

	@Override
	public void unbind(final SessionTutorial object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(SessionType.class, object.getSessionType());
		tuple = super.unbind(object, "title", "description", "sessionType", "startDate", "endDate", "link");
		tuple.put("sessionTypes", choices);
		tuple.put("tutorialId", super.getRequest().getData("tutorialId", int.class));
		super.getResponse().setData(tuple);
	}

	@Override
	public void validate(final SessionTutorial object) {
		if (MomentHelper.isAfter(object.getStartDate(), object.getEndDate())) {
			super.state(false, "timePeriodInitial", "student.activity.form.error.start.is.after.end");
			super.state(false, "timePeriodFinal", "student.activity.form.error.end.is.before.start");
		}
	}

	@Override
	public void perform(final SessionTutorial object) {
		assert object != null;

		final int tutorialId = super.getRequest().getData("tutorialId", int.class);
		object.setTutorial(this.repo.findTutorialById(tutorialId));
		this.repo.save(object);
	}
}
