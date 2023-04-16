
package acme.features.administrator.system_configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorSystemConfigurationUpdateService extends AbstractService<Administrator, SystemConfiguration> {

	@Autowired
	protected AdministratorSystemConfigurationRepository systemRepository;


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
	public void bind(final SystemConfiguration object) {
		assert object != null;

		super.bind(object, "systemCurrency", "listOfAcceptedCurrencies");

	}

	@Override
	public void validate(final SystemConfiguration object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("systemCurrency")) {
			final String[] currencies = object.getListOfAcceptedCurrencies().split(",");
			boolean currencyExists = false;
			for (final String currency : currencies)
				if (object.getSystemCurrency().equals(currency)) {
					currencyExists = true;
					break;
				}
			super.state(currencyExists, "systemCurrency", "administrator.config.form.error.unavailable");
		}
	}

	@Override
	public void perform(final SystemConfiguration object) {
		assert object != null;

		this.systemRepository.save(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "systemCurrency", "listOfAcceptedCurrencies");

		super.getResponse().setData(tuple);
	}

}
