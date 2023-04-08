
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferPostService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Offer object;
		object = new Offer();
		final Date actualDate = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(actualDate);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;
		super.bind(object, "heading", "summary", "availabilityPeriodInitial", "availabilityPeriodFinal", "price", "link");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodInitial")) {
			Date minimumStartDate;
			minimumStartDate = MomentHelper.deltaFromMoment(object.getInstantiationMoment(), 1, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getAvailabilityPeriodInitial(), minimumStartDate), "availabilityPeriodInitial", "administrator.offer.form.error.startavailabilityPeriodInitial");
		}

		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("availabilityPeriodInitial")) {
			Date maximumPeriod;
			maximumPeriod = MomentHelper.deltaFromMoment(object.getAvailabilityPeriodInitial(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getAvailabilityPeriodFinal(), maximumPeriod) && object.getAvailabilityPeriodFinal().after(object.getAvailabilityPeriodInitial()), "availabilityPeriodFinal",
				"administrator.offer.form.error.availabilityPeriodFinal");
		}
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodInitial", "availabilityPeriodFinal", "price", "link");
		super.getResponse().setData(tuple);

	}
}
