
package acme.entities.audits;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;
import acme.entities.courses.Course;


@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	@Column(unique = true)
	String code;

	@Size(max = 101)
	@NotBlank
	String	conclusion;

	@Size(max = 101)
	@NotBlank
	String	strongPoints;

	@Size(max = 101)
	@NotBlank
	String	weakPoints;
	
	Boolean draftMode = true;

	@ManyToOne
	Course course;

	@ManyToOne
	Auditor auditor;

}
