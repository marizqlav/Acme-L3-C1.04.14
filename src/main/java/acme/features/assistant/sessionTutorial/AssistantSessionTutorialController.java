
package acme.features.assistant.sessionTutorial;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.tutorial.SessionTutorial;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantSessionTutorialController extends AbstractController<Assistant, SessionTutorial> {

	@Autowired
	protected AssistantSessionTutorialListService	listService;

	@Autowired
	protected AssistantSessionTutorialShowService	showService;

	@Autowired
	protected AssistantSessionTutorialCreateService	createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);

		super.addBasicCommand("show", this.showService);

		super.addBasicCommand("create", this.createService);
		/*
		 * super.addBasicCommand("update", this.updateService);
		 * super.addBasicCommand("delete", this.deleteService);
		 * 
		 * super.addCustomCommand("publish", "update", this.publishService);
		 */
	}
}
