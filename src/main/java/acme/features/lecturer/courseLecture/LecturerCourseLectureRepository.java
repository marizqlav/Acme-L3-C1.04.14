
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courseLectures.CourseLecture;
import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerCourseLectureRepository extends AbstractRepository {

	@Query("select c.lecture from CourseLecture c where c.course.id = :id")
	Collection<Lecture> findAllLecturesByCourse(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id")
	Collection<Lecture> findAllLecturesByLecturer(int id);

	@Query("select l from Lecture l where l.id=:id")
	CourseLecture findAllLectures(int id);

	@Query("select c from Course c where c.id=:id")
	Course findCourseById(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findLectureById(int id);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	Collection<Lecture> findLecturesFromCourseLecture(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id and l.draftmode=0")
	Collection<Lecture> findPublishedLecturesFromLecturer(int id);

}
