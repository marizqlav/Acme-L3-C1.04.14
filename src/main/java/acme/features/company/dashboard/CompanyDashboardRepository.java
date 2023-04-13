
package acme.features.company.dashboard;

import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	//	@Query("select count(distinct l)  from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.lecturer = :lecturer and l.nature = :nature")
	//	Optional<Integer> findNumOfLecturesByType(Lecturer lecturer, Nature nature);
	//
	//	@Query("select avg(l.estimatedLearningTime) from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.lecturer = :lecturer")
	//	Optional<Double> findAverageLectureLearningTime(Lecturer lecturer);
	//
	//	@Query("select max(l.estimatedLearningTime) from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.lecturer = :lecturer")
	//	Optional<Double> findMaxLectureLearningTime(Lecturer lecturer);
	//
	//	@Query("select min(l.estimatedLearningTime) from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.lecturer = :lecturer")
	//	Optional<Double> findMinLectureLearningTime(Lecturer lecturer);
	//
	//	@Query("select stddev(l.estimatedLearningTime) from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.lecturer = :lecturer")
	//	Optional<Double> findLinearDevLectureLearningTime(Lecturer lecturer);
	//
	//	@Query("select l from Lecturer l where l.userAccount.id = :id")
	//	Lecturer findOneLecturerByUserAccountId(int id);
	//
	//	@Query("select ua from UserAccount ua where ua.id = :id")
	//	UserAccount findOneUserAccountById(int id);
	//
	//	@Query("select sum(l.estimatedLearningTime) from Course c join CourseLecture cl on c = cl.course join Lecture l on cl.lecture = l where c.lecturer = :lecturer group by c")
	//	Collection<Double> findEstimatedLearningTimeByCourse(Lecturer lecturer);
}
