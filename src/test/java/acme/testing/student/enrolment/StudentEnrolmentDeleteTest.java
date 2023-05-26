
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentDeleteTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String code, final String motivation, final String someGoals) {

		//This test login as a principal with "Student" role and try to delete the indicated enrolment.

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

	@Test
	public void hackingTest() {

		// This test tries to delete an enrolment with a role other than "Student",
		// or using an student who is not the owner.
		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments) {
			param = String.format("id=%d", enrolment.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/enrolment/delete", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/student/enrolment/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/student/enrolment/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/student/enrolment/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
