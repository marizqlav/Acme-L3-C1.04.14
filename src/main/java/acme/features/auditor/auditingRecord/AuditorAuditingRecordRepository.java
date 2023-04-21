package acme.features.auditor.auditingRecord;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditingRecordRepository extends AbstractRepository {

    @Query("SELECT a FROM AuditingRecord a WHERE a.audit.id = :auditId")
    List<AuditingRecord> findAllAuditingRecordsFromAudit(@Param("auditId") Integer auditId);

    @Query("SELECT a FROM AuditingRecord a WHERE a.id = :id")
    AuditingRecord findAuditingRecord(@Param("id") Integer id);

    @Query("SELECT a FROM Audit a WHERE a.id = :id")
    Audit findAudit(@Param("id") Integer id);

}