
package acme.entities.offer;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				instantiationMoment;

	@NotBlank
	@Length(max = 76)
	protected String			heading;

	@NotBlank
	@Length(max = 101)
	protected String			summary;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				availabilityPeriodInitial;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				availabilityPeriodFinal;

	@NotNull
	protected Money				price;

	@URL
	protected String			link;


	@Transient
	public Long getAvailabilityPeriod() {
		return MomentHelper.computeDuration(this.availabilityPeriodFinal, this.availabilityPeriodInitial).toMinutes() / 60;
	}

}
