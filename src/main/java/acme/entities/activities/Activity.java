
package acme.entities.activities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.enrolments.Enrolment;
import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Activity extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@NotNull
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@NotNull
	@Length(max = 101)
	protected String			abstractResumen;

	@NotNull
	protected ActivityType		activityType;

	@Temporal(TemporalType.TIMESTAMP) //Since it can be past as future, and as has been said in the forum, it will not be necessary to implement any time restriction
	@NotNull
	protected Date				timePeriodInitial;

	@Temporal(TemporalType.TIMESTAMP) //Must be after the timePeriodInitial date
	@NotNull
	protected Date				timePeriodFinal;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Double getTimePeriod() {
		return (double) (MomentHelper.computeDuration(this.timePeriodFinal, this.timePeriodInitial).toMinutes() / 60);
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne()
	protected Enrolment enrolment;
}
