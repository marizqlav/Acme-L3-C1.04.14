
package acme.entities.dashboards;

import java.util.Date;
import java.util.Map;

import acme.framework.data.AbstractEntity;

public class LecturerDashboard extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Derived attributes -----------------------------------------------------
	protected Integer			nTheoryLectures;

	protected Integer			nHandsOnLectures;

	protected Map<String, Date>	statisticsLecture; //average,deviation,min,max

	protected Map<String, Date>	statisticsCourses; //average,deviation,min,max

	// Relationships ----------------------------------------------------------

}
