
package acme.entities.bulletin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bulletin extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Column(name = "title")
	@NotBlank
	@Size(max = 76)
	protected String			title;

	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@PastOrPresent
	protected Date				date;

	@Column(name = "message")
	@NotBlank
	@Size(max = 101)
	protected String			message;

	// Critical is True if the bulletin is critical, false otherwise
	@Column(name = "critical")
	@NotNull
	protected Boolean			critical;

	@URL
	protected String			link;
}
