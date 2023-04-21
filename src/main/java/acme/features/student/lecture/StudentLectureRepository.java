
package acme.features.student.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

	@Query("select c.lecture from CourseLecture c where c.course.id = :id")
	Collection<Lecture> findAllLecturesFromCourseId(int id);

	@Query("SELECT l FROM Lecture l WHERE l.id = :id")
	Lecture findLectureById(@Param("id") Integer id);

	@Query("SELECT s FROM Student s WHERE s.id = :id")
	Student findStudentById(@Param("id") Integer id);

	@Query("select cl.course from CourseLecture cl where cl.lecture.id = :id")
	Course findOneCourseByLectureId(int id);

}
