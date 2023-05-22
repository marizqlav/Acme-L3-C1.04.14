
package acme.features.authenticated.offer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.features.rate.ComputeMoneyRate;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferShowService extends AbstractService<Authenticated, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedOfferRepository	offerRepository;

	@Autowired
	protected ComputeMoneyRate				computeMoneyRate;


	// AbstractService interface ----------------------------------------------ç
	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		int id;
		Offer offer;
		id = super.getRequest().getData("id", int.class);
		offer = this.offerRepository.findOfferById(id);
		final Date date = new Date();
		super.getResponse().setAuthorised(date.compareTo(offer.getAvailabilityPeriodFinal()) < 0);
	}

	@Override
	public void load() {
		Offer object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.offerRepository.findOfferById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;
		Double availabilityPeriod = null;
		availabilityPeriod = object.availabilityPeriod();
		final String systemCurrency = this.offerRepository.findSystemConfiguration().getSystemCurrency();
		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodInitial", "availabilityPeriodFinal", "price", "link");
		tuple.put("exchangeMoney", this.computeMoneyRate.computeMoneyExchange(object.getPrice(), systemCurrency).getTarget());
		tuple.put("availabilityPeriod", availabilityPeriod);
		super.getResponse().setData(tuple);
	}

}
