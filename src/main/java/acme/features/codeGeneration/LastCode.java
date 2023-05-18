package acme.features.codeGeneration;

import javax.persistence.Entity;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LastCode extends AbstractEntity {
    
    protected static final long	serialVersionUID	= 1L;

    String className;
    String lastCode;
}
