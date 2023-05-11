
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	Integer						nTheoryAudits;
	Integer						nHandsOnAudits;
	Map<String, Double>			auditingRecordsStatistics;
	Map<String, Double>			PeriodOfAuditingRecordsStatistics;

}
