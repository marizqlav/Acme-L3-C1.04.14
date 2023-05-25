
package acme.entities.audits;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditingRecord extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 76)
	String subject;

	@NotBlank
	@Size(max = 101)
	String assesment;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	Date assesmentStartDate;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	Date assesmentEndDate;
	
	@NotNull
	MarkType mark;

	boolean correction = false;
	
	@ManyToOne
	Audit audit;
	
}
