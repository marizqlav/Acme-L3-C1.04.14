
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Derived attributes -----------------------------------------------------
	protected Integer				nTheoryLectures;

	protected Integer				nHandsOnLectures;

	protected Map<String, Double>	statisticsLecture; //average,deviation,min,max

	protected Map<String, Double>	statisticsCourses; //average,deviation,min,max

	// Relationships ----------------------------------------------------------

}
