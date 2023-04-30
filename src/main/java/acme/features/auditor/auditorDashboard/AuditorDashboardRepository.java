
package acme.features.auditor.auditorDashboard;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.audits.AuditingRecord;
import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.entities.lectures.LectureType;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("SELECT count(a) from Audit a WHERE a.auditor.id = :auditorId AND a.course.id = :courseId")
	Integer countAuditsOfAuditorByCourse(@Param("courseId") Integer courseId, @Param("auditorId") Integer auditorId);

	@Query("SELECT c FROM Course c")
	List<Course> findCourses();

	@Query("SELECT cl.lecture FROM CourseLecture cl WHERE cl.course.id = :id")
	List<Lecture> findAllLecturesByCourse(@Param("id") Integer id);


	default Integer totalNumberOfTheoryAudits(Integer auditorId) {
		Integer res = 0;

		for (Course course : findCourses()) {
			if (courseType(findAllLecturesByCourse(course.getId())) == LectureType.THEORY) {
				
				res += countAuditsOfAuditorByCourse(course.getId(), auditorId);
			}
		}

		return res;
	}

	default Integer totalNumberOfHandsOnAudits(Integer auditorId) {
		Integer res = 0;

		for (Course course : findCourses()) {
			if (courseType(findAllLecturesByCourse(course.getId())) == LectureType.HANDSON) {
				
				res += countAuditsOfAuditorByCourse(course.getId(), auditorId);
			}
		}

		return res;
	}

	default LectureType courseType(final List<Lecture> lecturesCourse) {
		int theory = 0;
		int handsOn = 0;
		LectureType res = LectureType.THEORY;
		for (final Lecture l : lecturesCourse)
			if (l.getLectureType().equals(LectureType.THEORY)) {
				theory += 1;
			}
			else if (l.getLectureType().equals(LectureType.HANDSON)) {
				handsOn += 1;
			}
			if (theory < handsOn)
			res = LectureType.HANDSON;
		else if (theory == handsOn)
			res = LectureType.BALANCED;

		return res;
	}


	@Query("SELECT count(ar) FROM AuditingRecord ar WHERE ar.audit.auditor.id = :auditorId GROUP BY ar.audit")
	List<Integer> getCountOfAuditingRecordsOfAuditor(@Param("auditorId") Integer auditorId);

	default Map<String, Double> numberOfAuditingRecordsOfAuditStatistics(Integer auditorId) {
		List<Integer> count = getCountOfAuditingRecordsOfAuditor(auditorId);

		count.stream().forEach(x -> System.out.println(x));

		Map<String, Double> res = new HashMap<>();

		Double mean = count.stream().mapToDouble(x -> x).sum() / count.size();
		Double deviation = Math.sqrt(count.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / count.size());
		Double min = count.stream().mapToDouble(x -> x).min().getAsDouble();
		Double max = count.stream().mapToDouble(x -> x).max().getAsDouble();

		res.put("AVERAGE", mean);
		res.put("DEVIATION", deviation);
		res.put("MIN", min);
		res.put("MAX", max);

		return res;
	}

	// @Query("SELECT stddev(count(ar.audit)) FROM AuditingRecord ar JOIN Audit a ON ar.audit = a WHERE a.auditor.id = :auditorId GROUP BY a")
	// Double deviationNumberOfAuditingRecordsOfAuditor(@Param("auditorId") Integer auditorId);

	// @Query("SELECT min(count(ar.audit)) FROM AuditingRecord ar JOIN Audit a ON ar.audit = a WHERE a.auditor.id = :auditorId GROUP BY a")
	// Double minNumberOfAuditingRecordsOfAuditor(@Param("auditorId") Integer auditorId);

	// @Query("SELECT max(count(ar.audit)) FROM AuditingRecord ar JOIN Audit a ON ar.audit = a WHERE a.auditor.id = :auditorId GROUP BY a")
	// Double maxNumberOfAuditingRecordsOfAuditor(@Param("auditorId") Integer auditorId);


	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.auditor.id = :auditorId")
	List<AuditingRecord> findAuditingRecordsByAuditor(@Param("auditorId") Integer auditorId);

	default Map<String, Double> periodOfAuditingRecordsOfAuditorStatistics(@Param("auditorId") Integer auditorId) {
		List<Double> durations = findAuditingRecordsByAuditor(auditorId).stream()
			.map(x -> getPeriodOfAuditingRecordOnHours(x))
			.collect(Collectors.toList());

		Double mean = durations.stream().mapToDouble(x -> x).sum() / durations.size();
		Double deviation = Math.sqrt(durations.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / durations.size());
		Double min = durations.stream().mapToDouble(x -> x).min().getAsDouble();
		Double max = durations.stream().mapToDouble(x -> x).max().getAsDouble();

		Map<String, Double> res = new HashMap<>();
		res.put("AVERAGE", mean);
		res.put("DEVIATION", deviation);
		res.put("MIN", min);
		res.put("MAX", max);

		return res;
		
	}

	default Double getPeriodOfAuditingRecordOnHours(AuditingRecord auditingRecord) {
		LocalDateTime firstDate = auditingRecord.getAssesmentStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime lastDate = auditingRecord.getAssesmentEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		Long res = Duration.between(firstDate, lastDate).toHours();

		return res.doubleValue();
	}

}
