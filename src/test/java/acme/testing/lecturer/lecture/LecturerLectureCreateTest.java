
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerLectureCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String title, final String resumen, final String estimatedTime, final String body, final String lectureType) {

		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "Lecture List");

		super.checkListingExists();
		super.clickOnButton("Create Lecture");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("resumen", resumen);
		super.fillInputBoxIn("estimatedTime", estimatedTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("lectureType", lectureType);
		super.clickOnSubmit("Create Lecture");

		super.clickOnMenu("Lecturer", "Lecture List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("resumen", resumen);
		super.checkInputBoxHasValue("estimatedTime", estimatedTime);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("lectureType", lectureType);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeTest(final int recordIndex, final String title, final String resumen, final String estimatedTime, final String body, final String lectureType) {

		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "Lecture List");
		super.clickOnButton("Create Lecture");
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("resumen", resumen);
		super.fillInputBoxIn("estimatedTime", estimatedTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("lectureType", lectureType);
		super.clickOnSubmit("Create Lecture");

		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a job using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/lecturer/leture/create");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/lecturer/lecture/create");
		super.checkPanicExists();
		super.signOut();
	}

}
