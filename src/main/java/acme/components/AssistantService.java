
package acme.components;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.datatypes.Money;

@Service
public class AssistantService {

	@Autowired
	private AssistantRepository repository;


	public boolean validatePrice(final Money price, final Integer min, final Integer max) {
		final String acceptedCurrencies = this.repository.findSystemConfiguration().getListOfAcceptedCurrencies();
		final List<String> listOfAcceptedCurrencies = Arrays.asList(acceptedCurrencies.split(","));
		return price.getAmount() >= min && price.getAmount() < max && listOfAcceptedCurrencies.contains(price.getCurrency());
	}
}
