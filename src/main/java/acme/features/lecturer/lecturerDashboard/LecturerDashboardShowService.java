
package acme.features.lecturer.lecturerDashboard;

import java.beans.Transient;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.LecturerDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Principal principal;
		int lecturerId;

		principal = super.getRequest().getPrincipal();
		lecturerId = principal.getActiveRoleId();

		List<Double> learningTimeOfCourses;

		//clases
		LecturerDashboard lecturerDashboard;
		Integer totalNumberOfTheoryLectures;
		Integer totalNumberOfHandsOnLectures;
		Double averageLearningTimeOfLectures;
		Double deviationLearningTimeOfLectures;
		Double minimumLearningTimeOfLectures;
		Double maximumLearningTimeOfLectures;
		//cursos
		final Double averageLearningTimeOfCoursesCalc;
		final Double deviationLearningTimeOfCoursesCalc;
		final Double minimumLearningTimeOfCoursesCalc;
		final Double maximumLearningTimeOfCoursesCalc;

		learningTimeOfCourses = this.repository.learningTimeOfCourses(lecturerId);

		totalNumberOfTheoryLectures = this.repository.totalNumberOfTheoryLectures(lecturerId);
		totalNumberOfHandsOnLectures = this.repository.totalNumberOfHandsOnLectures(lecturerId);
		averageLearningTimeOfLectures = this.repository.averageLearningTimeOfLectures(lecturerId);
		deviationLearningTimeOfLectures = this.repository.deviationLearningTimeOfLectures(lecturerId);
		minimumLearningTimeOfLectures = this.repository.minimumLearningTimeOfLectures(lecturerId);
		maximumLearningTimeOfLectures = this.repository.maximumLearningTimeOfLectures(lecturerId);

		lecturerDashboard = new LecturerDashboard();

		averageLearningTimeOfCoursesCalc = this.averageLearningTimeOfCoursesCalc(learningTimeOfCourses);
		deviationLearningTimeOfCoursesCalc = this.deviationLearningTimeOfCoursesCalc(learningTimeOfCourses);
		minimumLearningTimeOfCoursesCalc = this.minimumLearningTimeOfCoursesCalc(learningTimeOfCourses);
		maximumLearningTimeOfCoursesCalc = this.maximumLearningTimeOfCoursesCalc(learningTimeOfCourses);

		lecturerDashboard.setNHandsOnLectures(totalNumberOfHandsOnLectures);
		lecturerDashboard.setNTheoryLectures(totalNumberOfTheoryLectures);

		final Map<String, Double> estadisticasLecture = new HashMap<>();
		estadisticasLecture.put("AVERAGE", averageLearningTimeOfLectures);
		estadisticasLecture.put("DEVIATION", deviationLearningTimeOfLectures);
		estadisticasLecture.put("MAX", maximumLearningTimeOfLectures);
		estadisticasLecture.put("MIN", minimumLearningTimeOfLectures);

		final Map<String, Double> estadisticasCourse = new HashMap<>();
		estadisticasCourse.put("AVERAGE", averageLearningTimeOfCoursesCalc);
		estadisticasCourse.put("DEVIATION", deviationLearningTimeOfCoursesCalc);
		estadisticasCourse.put("MAX", maximumLearningTimeOfCoursesCalc);
		estadisticasCourse.put("MIN", minimumLearningTimeOfCoursesCalc);

		lecturerDashboard.setStatisticsCourses(estadisticasCourse);

		lecturerDashboard.setStatisticsLecture(estadisticasLecture);

		super.getBuffer().setData(lecturerDashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "nTheoryLectures", "nHandsOnLectures", "statisticsLecture", "statisticsCourses");

		super.getResponse().setData(tuple);
	}

	@Transient
	public Double averageLearningTimeOfCoursesCalc(final List<Double> ls) {
		if (ls != null && !ls.isEmpty()) {
			double result = 0.;

			for (final Double n : ls)
				result += n;

			return result / ls.size();
		} else
			return null;
	}

	@Transient
	public Double deviationLearningTimeOfCoursesCalc(final List<Double> ls) {
		if (ls != null && !ls.isEmpty()) {
			double media = 0.;

			for (final Double n : ls)
				media += n;
			media /= ls.size();

			double sumaDesviacionesCuadrado = 0.;
			for (final Double n : ls) {
				final double desviacion = n - media;
				sumaDesviacionesCuadrado += desviacion * desviacion;
			}

			return Math.sqrt(sumaDesviacionesCuadrado / ls.size());
		} else
			return null;

	}

	@Transient
	public Double minimumLearningTimeOfCoursesCalc(final List<Double> ls) {
		if (ls != null && !ls.isEmpty())
			return Collections.min(ls);
		else
			return null;
	}

	@Transient
	public Double maximumLearningTimeOfCoursesCalc(final List<Double> ls) {
		if (ls != null && !ls.isEmpty())
			return Collections.max(ls);
		else
			return null;
	}

}
