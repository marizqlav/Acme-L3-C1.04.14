/*
 * EmployerJobShowTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.activities.Activity;
import acme.testing.TestHarness;

public class StudentActivityShowTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int activityRecordIndex, final String title, final String abstractResumen, final String activityType, final String timePeriodInitial, final String timePeriodFinal, final String timePeriod,
		final String link) {

		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkButtonExists("View activities");
		super.clickOnButton("View activities");
		super.checkListingExists();
		super.clickOnListingRecord(activityRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractResumen", abstractResumen);
		super.checkInputBoxHasValue("activityType", activityType);
		super.checkInputBoxHasValue("timePeriodInitial", timePeriodInitial);
		super.checkInputBoxHasValue("timePeriodFinal", timePeriodFinal);
		super.checkInputBoxHasValue("timePeriod", timePeriod);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {
		Collection<Activity> activities;
		String param;

		activities = this.repository.findManyActivitiesByStudentUsername("student1");
		for (final Activity activity : activities) {
			param = String.format("id=%d", activity.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/show", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/student/activity/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/student/activity/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
