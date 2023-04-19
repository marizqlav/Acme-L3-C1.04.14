
package acme.entities.sessionPracticum;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.practicum.Practicum;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SessionPracticum extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			abstractSessionPracticum;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finishDate;

	@URL
	protected String			link;

	boolean						draftMode;

	boolean						addendum;

	//	@Transient
	//	public Double getTimePeriod() {
	//		return (double) (MomentHelper.computeDuration(this.finishDate, this.startDate).toMinutes() / 60);
	//	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Practicum			practicum;
}
