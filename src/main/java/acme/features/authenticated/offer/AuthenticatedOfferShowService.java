
package acme.features.authenticated.offer;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.BinderHelper;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferShowService extends AbstractService<Authenticated, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedOfferRepository offerRepository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		final boolean status;
		final int id;
		final Offer offer;
		final Date date;

		id = super.getRequest().getData("id", int.class);
		offer = this.offerRepository.findOfferById(id);
		date = MomentHelper.deltaFromCurrentMoment(-7, ChronoUnit.DAYS);
		final Long period = offer.getAvailabilityPeriod();
		if (MomentHelper.isAfter(offer.getInstantiationMoment(), date) && MomentHelper.isLongEnough(offer.getAvailabilityPeriodInitial(), offer.getAvailabilityPeriodFinal(), period, ChronoUnit.DAYS))
			status = true;
		else
			status = false;
		super.getResponse().setAuthorised(status);
		//		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
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

		tuple = BinderHelper.unbind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodInitial", "availabilityPeriodFinal", "price", "link");
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
