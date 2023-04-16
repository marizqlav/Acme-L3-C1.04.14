
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
public class AdministratorBannerShowService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository bannerRepository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);
		
		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Banner banner;

		banner = this.bannerRepository.findBannerById(super.getRequest().getData("id", int.class));

		super.getBuffer().setData(banner);
	}

	
	public void unbind(final Banner banner) {
		assert banner != null;

		Tuple tuple;

		tuple = super.unbind(banner, "slogan", "displayPeriodFirstDate", "displayPeriodLastDate", "instantiationMoment", "linkPicture", "linkWeb");
				
		LocalDateTime firstDate = banner.getDisplayPeriodFirstDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime lastDate = banner.getDisplayPeriodLastDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		tuple.put("first", firstDate);
		tuple.put("last", lastDate);
		tuple.put("duration", Duration.between(firstDate, lastDate).toDays());

		super.getResponse().setData(tuple);
	}
}
