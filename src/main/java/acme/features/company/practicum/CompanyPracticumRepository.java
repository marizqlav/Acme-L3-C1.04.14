/*
 * AuthenticatedAnnouncementRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.company.id = :companyId")
	Collection<Practicum> findPracticaByCompanyId(int companyId);

	@Query("select p from Practicum p where p.id = :id")
	Practicum findPracticumById(int id);

	@Query("select c from Company c where c.id = :id")
	Company findCompanyById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select sp from SessionPracticum sp where sp.practicum.id = :id")
	Collection<SessionPracticum> findSessionPracticumByPracticumId(int id);

	@Query("select sum(TIME_TO_SEC(TIMEDIFF(sp.finishDate, sp.startDate)) / 3600) from SessionPracticum sp where sp.practicum.id= :practicumId")
	Double findEstimatedTimeSessionsPerPracticum(int practicumId);

	//	@Query("select p.code from Practicum p")
	//	Collection<String> findAllCodes();

	Practicum findFirstByOrderByCodeDesc();
}
