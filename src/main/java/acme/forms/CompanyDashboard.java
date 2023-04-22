
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	private static final long		serialVersionUID	= 1L;

	protected Map<String, Integer>	nPracticumByMonthLastYear;
	protected Map<String, Double>	statisticsPeriodLengthOfTheSessionsInTheirPractica;//average,deviation,min,max
	protected Map<String, Double>	statisticsPeriodLengthOfTheirPractica;

}
