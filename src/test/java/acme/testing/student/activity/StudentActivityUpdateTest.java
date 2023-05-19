
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.activities.Activity;
import acme.testing.TestHarness;

public class StudentActivityUpdateTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final int activityRecordIndex, final String title, final String abstractResumen, final String activityType, final String timePeriodInitial, final String timePeriodFinal, final String timePeriod,
		final String link) {

		// This test logs in as a student, lists his or her activities, 
		// selects one of them, updates it, and then checks that 
		// the update has actually been performed.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkButtonExists("View activities");
		super.clickOnButton("View activities");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(activityRecordIndex);

		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractResumen", abstractResumen);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("timePeriodInitial", timePeriodInitial);
		super.fillInputBoxIn("timePeriodFinal", timePeriodFinal);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");
		super.clickOnListingRecord(activityRecordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractResumen", abstractResumen);
		super.checkInputBoxHasValue("activityType", activityType);
		super.checkInputBoxHasValue("timePeriodInitial", timePeriodInitial);
		super.checkInputBoxHasValue("timePeriodFinal", timePeriodFinal);
		this.checkInputBoxHasValue("timePeriod", timePeriod);
		super.checkInputBoxHasValue("link", link);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeTest(final int recordIndex, final int activityRecordIndex, final String title, final String abstractResumen, final String activityType, final String timePeriodInitial, final String timePeriodFinal, final String link) {

		// This test attempts to update an activity with wrong data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkButtonExists("View activities");
		super.clickOnButton("View activities");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(activityRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractResumen", abstractResumen);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("timePeriodInitial", timePeriodInitial);
		super.fillInputBoxIn("timePeriodFinal", timePeriodFinal);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");
		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void hackingTest() {

		// This test tries to update an activity with a role other than "Student",
		// or using an student who is not the owner.
		Collection<Activity> activities;
		String param;

		activities = this.repository.findManyActivitiesByStudentUsername("student1");
		for (final Activity activity : activities) {
			param = String.format("id=%d", activity.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/update", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
