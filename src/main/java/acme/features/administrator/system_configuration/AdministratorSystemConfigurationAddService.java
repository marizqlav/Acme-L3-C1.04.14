/*
 * StudentEnrolmentShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.system_configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import acme.components.ExchangeRate;
import acme.datatypes.SystemConfiguration;
import acme.forms.MoneyExchange;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorSystemConfigurationAddService extends AbstractService<Administrator, SystemConfiguration> {

	@Autowired
	protected AdministratorSystemConfigurationRepository repository;


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
		admin = this.repository.findOneAdministratorById(adminId);
		status = admin != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SystemConfiguration object;

		object = this.repository.findSystemConfiguration();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SystemConfiguration object) {
		assert object != null;

		super.bind(object, "systemCurrency", "listOfAcceptedCurrencies");

	}

	public Boolean isValidMoney(final String targetCurrency) {
		MoneyExchange result;
		RestTemplate api;
		ExchangeRate record;
		String sourceCurrency;
		Double sourceAmount, targetAmount, rate;
		Money source, target;

		source = new Money();
		source.setAmount(1.);
		source.setCurrency("EUR");
		try {
			api = new RestTemplate();

			sourceCurrency = "EUR";
			sourceAmount = 1.;

			record = api.getForObject( //
				"https://api.exchangerate.host/latest?base={0}&symbols={1}", //
				ExchangeRate.class, //
				sourceCurrency, //
				targetCurrency //
			);

			assert record != null;
			rate = record.getRates().get(targetCurrency);
			targetAmount = rate * sourceAmount;

			target = new Money();
			target.setAmount(targetAmount);
			target.setCurrency(targetCurrency);

			result = new MoneyExchange();
			result.setSource(source);
			result.setTargetCurrency(targetCurrency);
			result.setDate(record.getDate());
			result.setTarget(target);
		} catch (final Throwable oops) {
			result = null;
		}

		if (result != null)
			return true;
		else
			return false;
	}

	@Override
	public void validate(final SystemConfiguration object) {
		assert object != null;
		final String newCurrency;
		newCurrency = super.getRequest().getData("newCurrency", String.class);

		if (newCurrency == null || newCurrency.isEmpty())
			super.state(false, "newCurrency", "new.currency.is.empty");
		else if (!newCurrency.matches("[A-Z]{3}"))
			super.state(false, "newCurrency", "new.currency.must.have.only.three.capital.letters");
		else if (!this.isValidMoney(newCurrency))
			super.state(false, "newCurrency", "new.currency.not.found.in.api");
		else if (object.getListOfAcceptedCurrencies().contains(newCurrency))
			super.state(false, "newCurrency", "new.currency.is.already.in.the.system");

	}

	@Override
	public void perform(final SystemConfiguration object) {
		assert object != null;

		String newCurrency;

		newCurrency = super.getRequest().getData("newCurrency", String.class);
		object.setListOfAcceptedCurrencies(object.getListOfAcceptedCurrencies() + "," + newCurrency);
		this.repository.save(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "systemCurrency", "listOfAcceptedCurrencies");

		super.getResponse().setData(tuple);
	}
}
