
package acme.datatypes;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "[A-Z]{3}")
	protected String			systemCurrency;

	protected String			listOfAcceptedCurrencies;
}
