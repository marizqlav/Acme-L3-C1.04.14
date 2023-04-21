
package acme.entities.tutorial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import acme.entities.courses.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Assistant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tutorial extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Boolean						draftMode;

	@Column(name = "title")
	@NotBlank
	@Size(max = 76)
	protected String			title;

	@Column(name = "description")
	@NotBlank
	@Size(max = 101)
	protected String			description;

	@Column(name = "estimatedTime")
	@PositiveOrZero
	protected double			estimatedTime;

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	@Column(unique = true)
	protected String			code;

	// Relationships ----------------------------------------------------------

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Course			course;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Assistant			assistant;
}
