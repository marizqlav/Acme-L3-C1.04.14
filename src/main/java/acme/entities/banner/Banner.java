package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {
    
    protected static final long	serialVersionUID = 1L;

    @Past
    Date instantiationMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date displayPeriodFirstDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	Date displayPeriodLastDate;

    @URL
    String linkPicture;

    @NotBlank
    @Size(max = 76)
    String slogan;

    @URL
    String linkWeb;

}
