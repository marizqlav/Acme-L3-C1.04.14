
package acme.features.administrator.system_configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorSystemConfigurationShowService extends AbstractService<Administrator, SystemConfiguration> {

	@Autowired
	protected AdministratorSystemConfigurationRepository systemRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int adminId;
		Administrator admin;

		adminId = super.getRequest().getPrincipal().getActiveRoleId();
		admin = this.systemRepository.findOneAdministratorById(adminId);
		status = admin != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SystemConfiguration object;

		object = this.systemRepository.findSystemConfiguration();

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		final Tuple tuple;

		tuple = super.unbind(object, "systemCurrency", "listOfAcceptedCurrencies");

		super.getResponse().setData(tuple);
	}
}
