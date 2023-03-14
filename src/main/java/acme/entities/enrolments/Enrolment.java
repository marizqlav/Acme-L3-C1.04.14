
package acme.entities.enrolments;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Student;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	@Column(unique = true)
	@NotBlank
	@NotNull
	protected String			code;

	@NotBlank
	@NotNull
	@Length(max = 76)
	protected String			motivation;

	@NotBlank
	@Length(max = 101)
	protected String			someGoals;

	protected boolean			draftMode;

	// Derived attributes -----------------------------------------------------

	//workTime must be implemented on service

	// Relationships ----------------------------------------------------------

	@Valid
	@ManyToOne()
	protected Student			student;

	/*
	 * @Valid
	 * 
	 * @ManyToOne()
	 * protected Course course;
	 */

}
