
package acme.datatypes;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import acme.framework.data.AbstractDatatype;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Period extends AbstractDatatype {

	private static final long serialVersionUID	= 1L;

	@Past
	Date firstDate;

	@Past
	Date lastDate;
	
	Duration getDuration() {
		LocalDateTime d1 = firstDate.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
		LocalDateTime d2 = lastDate.toInstant()
		      .atZone(ZoneId.systemDefault())
		      .toLocalDateTime();

		return Duration.between(d2, d1);
	}

}
