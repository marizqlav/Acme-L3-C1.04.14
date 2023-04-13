/*
 * StudentEnrolmentRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("select e from Enrolment e where e.code = :code")
	Enrolment findOneEnrolmentByCode(String code);

	@Query("select s from Student s where s.id = :id")
	Student findOneStudentById(int id);

	@Query("select e from Enrolment e where e.student.id = :studentId")
	Collection<Enrolment> findManyEnrolmentsByStudentId(int studentId);

	@Query("select e.course from Enrolment e where e.id = :id")
	Course findCourseByEnrolmnetId(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select s from Student s where s.id = :id")
	Student findStudentById(int id);
}
