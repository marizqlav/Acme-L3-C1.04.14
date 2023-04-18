/*
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.studentDashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentStudentDashboardRepository extends AbstractRepository {

	@Query("select count(a) from Activity a where a.activityType=0 and a.enrolment.student.id=:studentId")
	Integer totalNumberTheoryActivities(int studentId);

	@Query("select count(a) from Activity a where a.activityType=1 and a.enrolment.student.id=:studentId")
	Integer totalNumberHandsOnActivities(int studentId);

	@Query("select avg(TIME_TO_SEC(TIMEDIFF(a.timePeriodFinal, a.timePeriodInitial)) / 3600) from Activity a where a.enrolment.student.id=:studentId")
	Double averagePeriodActivitiesWorkbook(int studentId);

	@Query("select stddev(TIME_TO_SEC(TIMEDIFF(a.timePeriodFinal, a.timePeriodInitial)) / 3600) from Activity a where a.enrolment.student.id=:studentId")
	Double deviationPeriodActivitiesWorkbook(int studentId);

	@Query("select min(TIME_TO_SEC(TIMEDIFF(a.timePeriodFinal, a.timePeriodInitial)) / 3600) from Activity a where a.enrolment.student.id=:studentId")
	Double minimumPeriodActivitiesWorkbook(int studentId);

	@Query("select max(TIME_TO_SEC(TIMEDIFF(a.timePeriodFinal, a.timePeriodInitial)) / 3600) from Activity a where a.enrolment.student.id=:studentId")
	Double maximumPeriodActivitiesWorkbook(int studentId);

	@Query("select avg(select sum(l.estimatedTime) from CourseLecture cl join cl.lecture l where cl.course=c) from Enrolment e join e.course c where e.student.id=:studentId")
	Double averageLearningTimeCoursesEnrolled(int studentId);

	@Query("select stddev((select sum(l.estimatedTime) from CourseLecture cl join cl.lecture l where cl.course=c)) from Enrolment e join e.course c where e.student.id=:studentId")
	Double deviationLearningTimeCoursesEnrolled(int studentId);

	@Query("select min(select sum(l.estimatedTime) from CourseLecture cl join cl.lecture l where cl.course=c) from Enrolment e join e.course c where e.student.id=:studentId")
	Double minimumLearningTimeCoursesEnrolled(int studentId);

	@Query("select max(select sum(l.estimatedTime) from CourseLecture cl join cl.lecture l where cl.course=c) from Enrolment e join e.course c where e.student.id=:studentId")
	Double maximumLearningTimeCoursesEnrolled(int studentId);

}
