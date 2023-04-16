package acme.features.administrator.banner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository	repo;

	@Autowired
	protected BannerUtility bannerUtility;

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
		Banner banner = new Banner();

		super.getBuffer().setData(banner);
	}

	@Override
	public void bind(final Banner banner) {
		assert banner != null;

		banner.setInstantiationMoment(MomentHelper.getCurrentMoment());

		super.bind(banner, "slogan", "displayPeriodFirstDate", "displayPeriodLastDate", "linkPicture", "linkWeb");
	}

	@Override
	public void validate(final Banner banner) {
		assert banner != null;

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodFirstDate")) {
			if (banner.getDisplayPeriodFirstDate() != null) {
				super.state(banner.getDisplayPeriodFirstDate().after(banner.getInstantiationMoment()), "displayPeriodFirstDate", "administrator.banner.form.error.displayPeriodFirstDate.notFuture");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodLastDate")) {
			if (banner.getDisplayPeriodLastDate() != null) {
				super.state(banner.getDisplayPeriodLastDate().after(banner.getDisplayPeriodFirstDate()), "displayPeriodLastDate", "administrator.banner.form.error.displayPeriodLastDate.notFuture");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodLastDate")) {
			if (banner.getDisplayPeriodLastDate() != null) {
				LocalDateTime firstDate = banner.getDisplayPeriodFirstDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				LocalDateTime lastDate = banner.getDisplayPeriodLastDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		  
				super.state(Duration.between(firstDate, lastDate).toDays() >= 7, "displayPeriodLastDate", "administrator.banner.form.error.displayPeriodLastDate.notAWeek");
			}
		}
	}

	@Override
	public void perform(final Banner banner) {
		assert banner != null;

		repo.save(banner);

		bannerUtility.writeBannerData(banner);
	}

	@Override
	public void unbind(final Banner banner) {
		assert banner != null;

		Tuple tuple;
		
		tuple = super.unbind(banner, "slogan", "displayPeriodFirstDate", "displayPeriodLastDate", "instantiationMoment", "linkPicture", "linkWeb");

		super.getResponse().setData(tuple);
	}
}
