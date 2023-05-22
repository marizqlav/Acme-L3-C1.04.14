
package acme.testing.student.enrolment;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentEnrolmentDeleteTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String code, final String motivation, final String someGoals) {

		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "My enrolments");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("workTime", "0.00");
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("someGoals", someGoals);
		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.signOut();
	}

}
