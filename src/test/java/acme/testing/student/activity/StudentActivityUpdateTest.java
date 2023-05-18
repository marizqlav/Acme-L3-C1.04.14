
package acme.testing.student.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class StudentActivityUpdateTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final int activityRecordIndex, final String title, final String abstractResumen, final String activityType, final String timePeriodInitial, final String timePeriodFinal, final String timePeriod,
		final String link) {
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
		super.request("/student/activity/update");
		super.checkPanicExists();

		super.signIn("student1", "student1");
		super.request("/student/activity/update");
		super.checkPanicExists();
		super.signOut();
	}

}
