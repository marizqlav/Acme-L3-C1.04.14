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
    
    @Past
    Date instantiationMoment;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	Date firstDate;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	Date lastDate;

    @URL
    String linkPicture;

    @NotBlank
    @Size(max = 76)
    String slogan;

    @URL
    String linkWeb; 

}
