
package acme.features.assistant.session;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.tutorial.SessionTutorial;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantSessionController extends AbstractController<Assistant, SessionTutorial> {

	@Autowired
	protected AssistantSessionListService	listService;

	@Autowired
	protected AssistantSessionShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
