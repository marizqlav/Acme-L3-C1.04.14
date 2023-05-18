package acme.features.codeGeneration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface CodeRepository extends AbstractRepository {
    
    @Query("SELECT c.lastCode FROM LastCode c WHERE c.className = :className")
    public String getLastCode(@Param("className") String className);

    @Query("SELECT c FROM LastCode c WHERE c.className = :className")
    public LastCode getEntry(@Param("className") String className);
}
