
package acme.entities.practicum;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.courses.Course;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			abstractPracticum;

	@NotBlank
	@Length(max = 101)
	protected String			someGoals;

	@NotNull
	protected Boolean			draftMode;


	public Double estimatedTime(final Collection<SessionPracticum> sessions) {
		double estimatedTime = 0.;
		if (sessions.size() > 0)
			for (final SessionPracticum element : sessions) {
				final long durationInMilliseconds = element.getFinishDate().getTime() - element.getStartDate().getTime();
				final double durationInHours = durationInMilliseconds / (1000.0 * 60 * 60);
				estimatedTime = estimatedTime + durationInHours;
			}

		return estimatedTime;

	}


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Company	company;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Course	course;

}
