/*
 * EmployerJobCreateTest.java
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
import acme.entities.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityCreateTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final int activityRecordIndex, final String title, final String abstractResumen, final String activityType, final String timePeriodInitial, final String timePeriodFinal, final String link) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkButtonExists("View activities");
		super.clickOnButton("View activities");

		super.checkButtonExists("Create activity");
		super.clickOnButton("Create activity");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractResumen", abstractResumen);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("timePeriodInitial", timePeriodInitial);
		super.fillInputBoxIn("timePeriodFinal", timePeriodFinal);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create activity");

		super.checkListingExists();
		super.sortListing(3, "desc");
		super.checkColumnHasValue(activityRecordIndex, 0, title);
		super.checkColumnHasValue(activityRecordIndex, 1, abstractResumen);

		super.clickOnListingRecord(activityRecordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractResumen", abstractResumen);
		super.checkInputBoxHasValue("activityType", activityType);
		super.checkInputBoxHasValue("timePeriodInitial", timePeriodInitial);
		super.checkInputBoxHasValue("timePeriodFinal", timePeriodFinal);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeTest(final int recordIndex, final String title, final String abstractResumen, final String activityType, final String timePeriodInitial, final String timePeriodFinal, final String link) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkButtonExists("View activities");
		super.clickOnButton("View activities");

		super.checkButtonExists("Create activity");
		super.clickOnButton("Create activity");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractResumen", abstractResumen);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("timePeriodInitial", timePeriodInitial);
		super.fillInputBoxIn("timePeriodFinal", timePeriodFinal);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create activity");

		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {

		final Collection<Activity> activities;
		String param;

		activities = this.repository.findManyActivitiesByStudentUsername("student1");
		for (final Activity activity : activities) {
			param = String.format("masterId=%d", activity.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/create", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/student/activity/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {

		Collection<Enrolment> enrolments;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("student2", "student2");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments)
			if (enrolment.isDraftMode()) {
				param = String.format("masterId=%d", enrolment.getId());
				super.request("/student/activity/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {

		Collection<Enrolment> enrolments;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student2");
		for (final Enrolment enrolment : enrolments) {
			param = String.format("masterId=%d", enrolment.getId());
			super.request("/student/activity/create", param);
			super.checkPanicExists();
		}
	}

}
