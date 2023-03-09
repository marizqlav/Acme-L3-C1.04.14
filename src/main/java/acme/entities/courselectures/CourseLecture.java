
package acme.entities.courselectures;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CourseLecture extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@ManyToOne(optional = false)
	@JoinColumn(name = "course_id")
	protected Course			course;

	@ManyToOne(optional = false)
	@JoinColumn(name = "lecture_id")
	protected Lecture			lecture;

}
