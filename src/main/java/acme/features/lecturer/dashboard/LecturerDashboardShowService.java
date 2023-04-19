
package acme.features.lecturer.dashboard;

import java.util.HashMap;
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

		LecturerDashboard lecturerDashboard;
		Integer totalNumberOfTheoryLectures;
		Integer totalNumberOfHandsOnLectures;
		Double averageLearningTimeOfLectures;
		Double deviationLearningTimeOfLectures;
		Double minimumLearningTimeOfLectures;
		Double maximumLearningTimeOfLectures;

		totalNumberOfTheoryLectures = this.repository.totalNumberOfTheoryLectures(lecturerId);
		totalNumberOfHandsOnLectures = this.repository.totalNumberOfHandsOnLectures(lecturerId);
		averageLearningTimeOfLectures = this.repository.averageLearningTimeOfLectures(lecturerId);
		deviationLearningTimeOfLectures = this.repository.deviationLearningTimeOfLectures(lecturerId);
		minimumLearningTimeOfLectures = this.repository.minimumLearningTimeOfLectures(lecturerId);
		maximumLearningTimeOfLectures = this.repository.maximumLearningTimeOfLectures(lecturerId);

		lecturerDashboard = new LecturerDashboard();
		lecturerDashboard.setNHandsOnLectures(totalNumberOfHandsOnLectures);
		lecturerDashboard.setNTheoryLectures(totalNumberOfTheoryLectures);

		final Map<String, Double> estadisticasLecture = new HashMap<>();
		estadisticasLecture.put("AVERAGE", averageLearningTimeOfLectures);
		estadisticasLecture.put("DEVIATION", deviationLearningTimeOfLectures);
		estadisticasLecture.put("MAX", maximumLearningTimeOfLectures);
		estadisticasLecture.put("MIN", minimumLearningTimeOfLectures);

		lecturerDashboard.setStatisticsLecture(estadisticasLecture);

		super.getBuffer().setData(lecturerDashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "nTheoryLectures", "nHandsOnLectures", "statisticsLecture");

		super.getResponse().setData(tuple);
	}

}
