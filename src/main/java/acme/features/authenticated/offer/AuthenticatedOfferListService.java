
package acme.features.authenticated.offer;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferListService extends AbstractService<Authenticated, Offer> {

	@Autowired
	protected AuthenticatedOfferRepository offerRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Collection<Offer> objects;
		Date date;
		date = MomentHelper.deltaFromCurrentMoment(-7, ChronoUnit.DAYS);
		objects = this.offerRepository.findAllOffer(date);

		super.getBuffer().setData(objects);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodInitial", "availabilityPeriodFinal", "price", "link");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.offerRepository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodInitial", "availabilityPeriodFinal", "price", "link");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
