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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.StudentDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentStudentDashboardShowService extends AbstractService<Student, StudentDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentStudentDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Principal principal;

		principal = super.getRequest().getPrincipal();
		final int principalId = principal.getActiveRoleId();

		StudentDashboard dashboard;
		Map<String, Double> statisticsPeriodActivitiesWorkbook;
		Map<String, Double> statisticsLearningTimeCoursesEnrolled;

		Integer totalNumberTheoryActivities;
		Integer totalNumberHandsOnActivities;

		Double averagePeriodActivitiesWorkbook;
		Double deviationPeriodActivitiesWorkbook;
		Double minimumPeriodActivitiesWorkbook;
		Double maximumPeriodActivitiesWorkbook;

		Double averageLearningTimeCoursesEnrolled;
		Double deviationLearningTimeCoursesEnrolled;
		Double minimumLearningTimeCoursesEnrolled;
		Double maximumLearningTimeCoursesEnrolled;

		totalNumberTheoryActivities = this.repository.totalNumberTheoryActivities(principalId);
		totalNumberHandsOnActivities = this.repository.totalNumberHandsOnActivities(principalId);

		averagePeriodActivitiesWorkbook = this.repository.averagePeriodActivitiesWorkbook(principalId);
		deviationPeriodActivitiesWorkbook = this.repository.deviationPeriodActivitiesWorkbook(principalId);
		minimumPeriodActivitiesWorkbook = this.repository.minimumPeriodActivitiesWorkbook(principalId);
		maximumPeriodActivitiesWorkbook = this.repository.maximumPeriodActivitiesWorkbook(principalId);

		averageLearningTimeCoursesEnrolled = this.repository.averageLearningTimeCoursesEnrolled(principalId);
		deviationLearningTimeCoursesEnrolled = this.repository.deviationLearningTimeCoursesEnrolled(principalId);
		minimumLearningTimeCoursesEnrolled = this.repository.minimumLearningTimeCoursesEnrolled(principalId);
		maximumLearningTimeCoursesEnrolled = this.repository.maximumLearningTimeCoursesEnrolled(principalId);

		statisticsPeriodActivitiesWorkbook = new HashMap<String, Double>();
		statisticsPeriodActivitiesWorkbook.put("Avg", averagePeriodActivitiesWorkbook);
		statisticsPeriodActivitiesWorkbook.put("Deviation", deviationPeriodActivitiesWorkbook);
		statisticsPeriodActivitiesWorkbook.put("Min", minimumPeriodActivitiesWorkbook);
		statisticsPeriodActivitiesWorkbook.put("Max", maximumPeriodActivitiesWorkbook);

		statisticsLearningTimeCoursesEnrolled = new HashMap<String, Double>();
		statisticsLearningTimeCoursesEnrolled.put("Avg", averageLearningTimeCoursesEnrolled);
		statisticsLearningTimeCoursesEnrolled.put("Deviation", deviationLearningTimeCoursesEnrolled);
		statisticsLearningTimeCoursesEnrolled.put("Min", minimumLearningTimeCoursesEnrolled);
		statisticsLearningTimeCoursesEnrolled.put("Max", maximumLearningTimeCoursesEnrolled);

		dashboard = new StudentDashboard();
		dashboard.setTotalNumberTheoryActivities(totalNumberTheoryActivities);
		dashboard.setTotalNumberHandsOnActivities(totalNumberHandsOnActivities);
		dashboard.setStatisticsPeriodActivitiesWorkbook(statisticsPeriodActivitiesWorkbook);
		dashboard.setStatisticsLearningTimeCoursesEnrolled(statisticsLearningTimeCoursesEnrolled);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final StudentDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalNumberTheoryActivities", "totalNumberHandsOnActivities", "statisticsPeriodActivitiesWorkbook", "statisticsLearningTimeCoursesEnrolled");

		super.getResponse().setData(tuple);
	}

}
