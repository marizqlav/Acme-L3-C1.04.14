
package acme.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 76)
	String firm;

	@NotBlank
	@Size(max = 26)
	@Column(unique = true)
	String professionalID;

	@NotBlank
	@Size(max = 101)
	String listOfCertifications;

	@URL
	String link;

}
