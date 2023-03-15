
package acme.forms;

import java.util.Date;
import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Map<String, Integer>		nPracticumOfTheoryCoursesByMonthLastYear;
	Map<String, Integer>		nPracticumOfHandsOnCoursesByMonthLastYear;
	Map<String, Date>			statisticsPeriodLengthOfTheSessionsInTheirPractica;
	Map<String, Date>			statisticsPeriodLengthOfTheirPractica;

}
