package acme.features.authenticated.audit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuthenticatedAuditRepository extends AbstractRepository {

    @Query("SELECT a FROM Audit a WHERE a.course.id = :courseId")
    List<Audit> findAllAuditsByCourse(@Param("courseId") Integer courseId);

    @Query("SELECT a FROM Auditor a WHERE a.id = (SELECT audit.auditor.id FROM Audit audit WHERE audit.id = :id)")
    Auditor findAuditorByAuditId(@Param("id") Integer id);

    @Query("SELECT a FROM Audit a WHERE a.id = :id")
    Audit findAudit(@Param("id") Integer id);

    @Query("SELECT c FROM Course c")
    List<Course> findAllCourses();

    @Query("SELECT c FROM Course c WHERE c.id = :id")
    Course findCourse(@Param("id") Integer id);
}
