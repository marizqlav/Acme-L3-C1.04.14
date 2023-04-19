
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

	@Query("SELECT DISTINCT(l) FROM Lecture l LEFT JOIN CourseLecture cl ON l.id = cl.lecture.id WHERE cl.course.id != :courseId AND l.lecturer.id = :lecturerId")
	Collection<Lecture> findLecturesAvailableForACourse(int courseId, int lecturerId);

}
