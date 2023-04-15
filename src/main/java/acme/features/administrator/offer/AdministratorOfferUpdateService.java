
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AssistantService;
import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferRepository	repository;

	@Autowired
	protected AssistantService				assistantService;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Offer object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOfferById(id);
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

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(this.assistantService.validatePrice(object.getPrice(), 0, 1000000), "price", "administrator.offer.form.error.price");
		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodInitial")) {
			Date minimumStartDate;
			minimumStartDate = MomentHelper.deltaFromMoment(object.getInstantiationMoment(), 1, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getAvailabilityPeriodInitial(), minimumStartDate), "availabilityPeriodInitial", "administrator.offer.form.error.availabilityPeriodInitial");
		}

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodFinal") && !super.getBuffer().getErrors().hasErrors("availabilityPeriodInitial")) {
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
