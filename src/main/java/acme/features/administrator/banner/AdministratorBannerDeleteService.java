package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);
		
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		Banner banner = repo.findBannerById(super.getRequest().getData("id", int.class));
		status = banner != null;
		
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner banner;

		banner = repo.findBannerById(super.getRequest().getData("id", int.class));

		super.getBuffer().setData(banner);
	}

	@Override
	public void bind(final Banner banner) {
		assert banner != null;

		super.bind(banner, "slogan", "displayPeriodFirstDate", "displayPeriodLastDate", "linkPicture", "linkWeb");
	}

	@Override
	public void validate(final Banner banner) {
		assert banner != null;
	}

	@Override
	public void perform(final Banner banner) {
		assert banner != null;

		repo.delete(banner);
	}

	@Override
	public void unbind(final Banner banner) {
		assert banner != null;

		Tuple tuple;
		tuple = super.unbind(banner, "slogan", "displayPeriodFirstDate", "displayPeriodLastDate", "instantiationMoment", "linkPicture", "linkWeb");

		super.getResponse().setData(tuple);
	}
}
