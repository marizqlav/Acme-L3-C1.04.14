
package acme.features.auditor.auditorDashboard;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.AuditorDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorDashboardRepository repo;

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

		final Integer auditorId = super.getRequest().getPrincipal().getActiveRoleId();

		final AuditorDashboard dashboard = new AuditorDashboard();

		dashboard.setNTheoryAudits(this.repo.totalNumberOfTheoryAudits(auditorId));
		dashboard.setNHandsOnAudits(this.repo.totalNumberOfHandsOnAudits(auditorId));

		final Map<String, Double> auditingRecordsStatistics = this.repo.numberOfAuditingRecordsOfAuditStatistics(auditorId);

		final Map<String, Double> periodOfAuditingRecordsStatistics = this.repo.periodOfAuditingRecordsOfAuditorStatistics(auditorId);

		dashboard.setAuditingRecordsStatistics(auditingRecordsStatistics);

		dashboard.setPeriodOfAuditingRecordsStatistics(periodOfAuditingRecordsStatistics);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "nTheoryAudits", "nHandsOnAudits", "auditingRecordsStatistics", "periodOfAuditingRecordsStatistics");

		super.getResponse().setData(tuple);
	}

}
