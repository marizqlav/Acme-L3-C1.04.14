package acme.audits.dashboards;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {
    
    Integer nTheoryAudits;
    Integer nHandsOnAudits;
    Map<String, Double> averageDeviationMinMaxAuditingRecordsOfAudit;
    Map<String, Double> averageDeviationMinMaxOfPeriodOfAudit;

}
