
package acme.roles;

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
public class Assistant extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Size(max = 76)
	String						supervisor;

	@NotBlank
	@Size(max = 101)
	String						expertiseFields;

	@NotBlank
	@Size(max = 101)
	String						resume;

	@URL
	String						link;

}
