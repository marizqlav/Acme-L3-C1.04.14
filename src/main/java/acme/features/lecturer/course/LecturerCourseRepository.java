/*
 * AuthenticatedStudentRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.SystemConfiguration;
import acme.entities.audits.Audit;
import acme.entities.courseLectures.CourseLecture;
import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select c from Course c where c.lecturer.id =:id")
	Collection<Course> findAllCoursesByLecturer(int id);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	Collection<Lecture> findAllLecturesByCourse(int id);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findLecturerById(int id);

	Course findFirstByOrderByCodeDesc();

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();

	@Query("select cl from CourseLecture cl where cl.course.id = :id")
	Collection<CourseLecture> findCLfromCourse(int id);

	@Query("select p from Practicum p where  p.course.id = :courseId")
	Collection<Practicum> findPracticumCourse(int courseId);

	@Query("select a from Audit a where  a.course.id = :courseId")
	Collection<Audit> findAuditCourse(int courseId);

}
