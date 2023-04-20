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

package acme.features.any.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.SystemConfiguration;
import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyCourseRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	Collection<Lecture> findAllLecturesByCourse(int id);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();

}
