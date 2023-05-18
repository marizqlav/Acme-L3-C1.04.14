
package acme.testing.student.activity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivityDeleteTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final int activityRecordIndex, final String title, final String abstractResumen, final String activityType, final String timePeriodInitial, final String timePeriodFinal, final String link) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkButtonExists("View activities");
		super.clickOnButton("View activities");

		super.sortListing(0, "asc");
		super.checkColumnHasValue(activityRecordIndex, 0, title);
		super.checkColumnHasValue(activityRecordIndex, 1, abstractResumen);

		super.clickOnListingRecord(activityRecordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractResumen", abstractResumen);
		super.checkInputBoxHasValue("activityType", activityType);
		super.checkInputBoxHasValue("timePeriodInitial", timePeriodInitial);
		super.checkInputBoxHasValue("timePeriodFinal", timePeriodFinal);
		super.checkInputBoxHasValue("link", link);

		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.signOut();
	}

}
