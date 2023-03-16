package acme.entities.audits;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditRepository extends AbstractRepository {
    

    @Query("SELECT a FROM AuditingRecord a WHERE a.audit.id = audit.id")
    List<AuditingRecord> getAuditingRecords(@Param("audit") Audit audit);

}
