
package acme.features.administrator.banner;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerListService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repo;


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
		List<Banner> banners = repo.findAllBanners();
		super.getBuffer().setData(banners);
	}

	@Override
	public void unbind(final Banner banner) {
		assert banner != null;

		Tuple tuple;

		tuple = super.unbind(banner, "slogan", "displayPeriodFirstDate", "displayPeriodLastDate", "instantiationMoment", "linkPicture", "linkWeb");

		super.getResponse().setData(tuple);
	}
}
