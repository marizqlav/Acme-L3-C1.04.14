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

package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courseLectures.CourseLecture;
import acme.entities.lectures.Lecture;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findLectureById(int id);

	@Query("select l from Lecture l")
	Collection<Lecture> findAllLectures();

	@Query("select l from Lecture l where l.lecturer.id = :id")
	Collection<Lecture> findAllLecturesByLecturer(int id);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findLecturerById(int id);

	@Query("select cl from CourseLecture cl where cl.lecture = :lecture")
	Collection<CourseLecture> findCourseLecturesByLecture(Lecture lecture);

	@Query("select c.lecture from CourseLecture c where c.course.id = :id")
	Collection<Lecture> findAllLecturesByCourse(int id);

}
