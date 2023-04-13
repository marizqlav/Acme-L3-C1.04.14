
package acme.features.company.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.CompanyDashboard;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyDashboardShowService extends AbstractService<Company, CompanyDashboard> {

	@Autowired
	protected CompanyDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


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
		final CompanyDashboard dashboard = new CompanyDashboard();

		super.getBuffer().setData(true);
	}

	@Override
	public void unbind(final CompanyDashboard object) {
		//		Tuple tuple;

		//		tuple = super.unbind(object, "lecturesStats", "numOfLecturesByType", "coursesStats");

		super.getResponse().setData(true);
	}
}
