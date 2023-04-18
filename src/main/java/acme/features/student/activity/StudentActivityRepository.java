
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.activities.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("select a from Activity a where a.enrolment.id = :id")
	Collection<Activity> findAllActivitiesFromEnrolmentId(int id);

	@Query("SELECT a FROM Activity a WHERE a.id = :id")
	Activity findActivityById(@Param("id") Integer id);

	@Query("SELECT s FROM Student s WHERE s.id = :id")
	Student findStudentById(@Param("id") Integer id);

	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("SELECT a.enrolment FROM Activity a WHERE a.id = :id")
	Enrolment findOneEnrolmentByActivityId(@Param("id") Integer id);

	@Query("select a from Activity a where a.id = :id")
	Activity findOneActivityById(int id);

}
