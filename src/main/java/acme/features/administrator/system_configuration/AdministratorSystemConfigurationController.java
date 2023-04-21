
package acme.features.administrator.system_configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.datatypes.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorSystemConfigurationController extends AbstractController<Administrator, SystemConfiguration> {

	@Autowired
	protected AdministratorSystemConfigurationShowService	showService;

	@Autowired
	protected AdministratorSystemConfigurationUpdateService	updateService;

	@Autowired
	protected AdministratorSystemConfigurationAddService	addService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("add", "update", this.addService);
	}
}
