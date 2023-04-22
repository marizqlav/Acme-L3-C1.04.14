
package acme.features.company.dashboard;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.CompanyDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyDashboardShowService extends AbstractService<Company, CompanyDashboard> {

	@Autowired
	private CompanyDashboardRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		//		boolean status;
		//		Company company;
		//		Principal principal;
		//		int userAccountId;
		//
		//		principal = super.getRequest().getPrincipal();
		//		userAccountId = principal.getAccountId();
		//		company = this.repository.findCompanyByUserAccountId(userAccountId);
		//
		//		status = company != null && principal.hasRole(Company.class);

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CompanyDashboard companyDashboard;
		final int userAccountId;
		final Company company;

		Principal principal;
		int companyId;

		principal = super.getRequest().getPrincipal();
		companyId = principal.getActiveRoleId();

		double averageSessionPracticumLength;
		double deviationSessionPracticumLength;
		double minimumSessionPracticumLength;
		double maximumSessionPracticumLength;

		double averagePracticaLength;
		double deviationPracticaLength;
		double minimumPracticaLength;
		double maximumPracticaLength;

		final Map<String, Integer> totalNumberOfPracticaByMonth;

		//		userAccountId = principal.getAccountId();
		//		company = this.repository.findCompanyByUserAccountId(userAccountId);

		averageSessionPracticumLength = this.repository.findAverageSessionLength(companyId);
		deviationSessionPracticumLength = this.repository.findDeviationSessionLength(companyId);
		minimumSessionPracticumLength = this.repository.findMinimumSessionLength(companyId);
		maximumSessionPracticumLength = this.repository.findMaximumSessionLength(companyId);

		final Map<String, Double> estatisticsPeriodLengthOfTheSessionsInTheirPractica = new HashMap<>();
		estatisticsPeriodLengthOfTheSessionsInTheirPractica.put("AVERAGE", averageSessionPracticumLength);
		estatisticsPeriodLengthOfTheSessionsInTheirPractica.put("DEVIATION", deviationSessionPracticumLength);
		estatisticsPeriodLengthOfTheSessionsInTheirPractica.put("MAX", minimumSessionPracticumLength);
		estatisticsPeriodLengthOfTheSessionsInTheirPractica.put("MIN", maximumSessionPracticumLength);

		averagePracticaLength = this.repository.findAveragePracticaLength(companyId);
		deviationPracticaLength = this.repository.findDeviationPracticaLength(companyId);
		minimumPracticaLength = this.repository.findMinimumPracticaLength(companyId);
		maximumPracticaLength = this.repository.findMaximumPracticaLength(companyId);

		final Map<String, Double> estatisticsPeriodLengthOfTheirPractica = new HashMap<>();
		estatisticsPeriodLengthOfTheirPractica.put("AVERAGE", averagePracticaLength);
		estatisticsPeriodLengthOfTheirPractica.put("DEVIATION", deviationPracticaLength);
		estatisticsPeriodLengthOfTheirPractica.put("MAX", minimumPracticaLength);
		estatisticsPeriodLengthOfTheirPractica.put("MIN", maximumPracticaLength);

		totalNumberOfPracticaByMonth = this.repository.findTotalNumberOfPracticaByMonth(companyId).stream().collect(Collectors.toMap(key -> Month.of((int) key[0]).toString(), value -> (int) value[1]));

		companyDashboard = new CompanyDashboard();

		companyDashboard.setNPracticumByMonthLastYear(totalNumberOfPracticaByMonth);
		companyDashboard.setStatisticsPeriodLengthOfTheSessionsInTheirPractica(estatisticsPeriodLengthOfTheSessionsInTheirPractica);
		companyDashboard.setStatisticsPeriodLengthOfTheirPractica(estatisticsPeriodLengthOfTheirPractica);

		super.getBuffer().setData(companyDashboard);
	}

	@Override
	public void unbind(final CompanyDashboard companyDashboard) {
		Tuple tuple;

		tuple = super.unbind(companyDashboard, "nPracticumByMonthLastYear", "statisticsPeriodLengthOfTheSessionsInTheirPractica", "statisticsPeriodLengthOfTheirPractica");

		super.getResponse().setData(tuple);
	}
}
