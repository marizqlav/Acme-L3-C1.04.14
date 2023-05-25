package acme.features.auditor.audit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

    @Query("SELECT a FROM Audit a WHERE a.auditor.id = :id")
    List<Audit> findAllAuditsFromAuditor(@Param("id") Integer id);

    @Query("SELECT a FROM Audit a WHERE a.id = :id")
    Audit findAudit(@Param("id") Integer id);

    @Query("SELECT c FROM Course c WHERE c.id = :id")
    Course findCourse(@Param("id") Integer id);

    @Query("SELECT a FROM Auditor a WHERE a.id = :id")
    Auditor findAuditor(@Param("id") Integer id);

    @Query("SELECT c FROM Course c")
    List<Course> findAllCourses();

    @Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.id = :auditId")
    List<AuditingRecord> findRecordsFromAudit(@Param("auditId") Integer auditId);

    @Query("SELECT a FROM Audit a WHERE a.code = :code")
    Audit findByCode(@Param("code") String code);
}