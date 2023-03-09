
package acme.audits;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import acme.datatypes.Period;

@Entity
@Getter
@Setter
public class AuditingRecords extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 76)
	String	subject;

	@NotBlank
	@Size(max = 101)
	String	assesment;

	Period auditionPeriod;
	
	String mark;
	
	@ManyToOne
	Audit audit;
	
}
