
package acme.forms;

import java.util.Date;
import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	private static final long		serialVersionUID	= 1L;

	protected Map<String, Integer>	nPracticumByMonthLastYear;
	protected Map<String, Date>		statisticsPeriodLengthOfTheSessionsInTheirPractica;
	protected Map<String, Date>		statisticsPeriodLengthOfTheirPractica;

}
