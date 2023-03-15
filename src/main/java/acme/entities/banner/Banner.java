package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.datatypes.Period;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {
    
    @Past
    Date instantiationMoment;

    Period displayPeriod;

    @URL
    String linkPicture;

    @NotBlank
    @Size(max = 76)
    String slogan;

    @URL
    String linkWeb; 

}
