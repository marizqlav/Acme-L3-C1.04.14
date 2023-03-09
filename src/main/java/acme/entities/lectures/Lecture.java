
package acme.entities.lectures;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			resumen;

	@Enumerated(EnumType.STRING)
	protected LectureType		lectureType;

	@Positive
	protected Double			estimatedTime;

	@Length(max = 101)
	protected String			body;
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	//relacion con profesor
}
