
package acme.datatypes;

import java.util.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import acme.framework.data.AbstractDatatype;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Period extends AbstractDatatype {

	private static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date firstDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date lastDate;
	
	@Transient
	Duration getDuration() {
		final LocalDateTime d1 = this.firstDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		final LocalDateTime d2 = this.lastDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		return Duration.between(d2, d1);
	}

}
