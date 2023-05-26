
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentFinaliseTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String code, final String holderName, final String lowerNibble, final String expireDate, final String cvc) {

		// This test authenticates as a student, lists his or her enrolments,
		// then selects one of them, and finalise it.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkButtonExists("Finalise enrolment");
		super.clickOnButton("Finalise enrolment");

		super.checkFormExists();
		super.fillInputBoxIn("holderName", holderName);
		super.fillInputBoxIn("lowerNibble", lowerNibble);
		super.fillInputBoxIn("expireDate", expireDate);
		super.fillInputBoxIn("cvc", cvc);
		super.clickOnSubmit("Finalise");
		super.checkNotErrorsExist();

		super.checkButtonExists("View activities");
		super.clickOnButton("View activities");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeButtonTest(final int recordIndex, final String code, final String holderName, final String lowerNibble, final String expireDate, final String cvc) {

		// This test attempts to finalise an enrolment that cannot be finalised, yet.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkButtonExists("Finalise enrolment");
		super.clickOnButton("Finalise enrolment");

		super.checkFormExists();
		super.fillInputBoxIn("holderName", holderName);
		super.fillInputBoxIn("lowerNibble", lowerNibble);
		super.fillInputBoxIn("expireDate", expireDate);
		super.fillInputBoxIn("cvc", cvc);
		super.clickOnSubmit("Finalise");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		// This test tries to finalise an enrolment with a role other than "Student"
		// or using a student who is not the owner.

		final Collection<Enrolment> enrolments;
		String params;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments)
			if (enrolment.isDraftMode()) {
				params = String.format("id=%d", enrolment.getId());

				super.checkLinkExists("Sign in");
				super.request("/student/enrolment/finalise", params);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/student/enrolment/finalise", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student2", "student2");
				super.request("/student/enrolment/finalise", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/student/enrolment/finalise", params);
				super.checkPanicExists();
				super.signOut();

			}
	}

	@Test
	public void test301Hacking() {

		// This test tries to finalise a finalised enrolment that was registered by the principal.

		Collection<Enrolment> enrolments;
		String params;

		super.signIn("student1", "student1");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments)
			if (!enrolment.isDraftMode()) {
				params = String.format("id=%d", enrolment.getId());
				super.request("/student/enrolment/finalise", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {

		// This test tries to finalise an enrolment that wasn't registered by the principal,
		// be it finalised or not finalised.

		Collection<Enrolment> enrolments;
		String params;

		super.signIn("student2", "student2");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments) {
			params = String.format("id=%d", enrolment.getId());
			super.request("/student/enrolment/finalise", params);
		}
		super.signOut();
	}

}
