
package acme.entities.lectures;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
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
	@Column(unique = true)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			resumen;

	@NotNull
	protected LectureType		lectureType;

	@Min(1)
	@NotNull
	protected Double			estimatedTime;

	@NotBlank
	@Length(max = 101)
	protected String			body;

	@NotNull
	protected Boolean			draftmode;
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Lecturer			lecturer;

}
