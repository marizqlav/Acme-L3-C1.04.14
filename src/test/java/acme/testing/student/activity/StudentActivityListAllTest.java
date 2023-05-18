/*
 * EmployerJobListMineTest.java
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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivityListAllTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final int activityRecordIndex, final String title, final String abstractResumen, final String activityType, final String timePeriod) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkButtonExists("View activities");
		super.clickOnButton("View activities");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(activityRecordIndex, 0, title);
		super.checkColumnHasValue(activityRecordIndex, 1, abstractResumen);
		super.checkColumnHasValue(activityRecordIndex, 2, activityType);
		super.checkColumnHasValue(activityRecordIndex, 3, timePeriod);

		super.signOut();

	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/student/activity/list");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/student/activity/list");
		super.checkPanicExists();
		super.signOut();

	}

}
