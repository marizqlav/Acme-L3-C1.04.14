
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentUpdateTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	//@Order(10)
	public void positiveTest(final int recordIndex, final String code, final String courseTitle, final String motivation, final String someGoals) {

		// This test logs in as a student, lists his or her enrolments, 
		// selects one of them, updates it, and then checks that 
		// the update has actually been performed.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("workTime", "0.00");
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("someGoals", someGoals);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, courseTitle);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("workTime", "0.00");
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("someGoals", someGoals);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	//@Order(20)
	public void negativeTest(final int recordIndex, final String code, final String motivation, final String someGoals) {
		super.signIn("student1", "student1");

		// This test attempts to update an enrolment with wrong data.

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkFormExists();
		super.checkInputBoxHasValue("workTime", "0.00");
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("someGoals", someGoals);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void hackingTest() {

		// This test tries to update an enrolment with a role other than "Student",
		// or using a student who is not the owner.

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student");
		for (final Enrolment enrolment : enrolments) {
			param = String.format("id=%d", enrolment.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

}
