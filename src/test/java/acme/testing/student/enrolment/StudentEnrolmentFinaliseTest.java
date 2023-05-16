
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

		super.clickOnButton("View activities");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeButtonTest(final int recordIndex, final String code, final String holderName, final String lowerNibble, final String expireDate, final String cvc) {

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

			}
	}

	@Test
	public void test301Hacking() {

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

		Collection<Enrolment> enrolments;
		String params;

		super.signIn("student2", "student2");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments) {
			params = String.format("id=%d", enrolment.getId());
			super.request("/company/practicum/publish", params);
		}
		super.signOut();
	}

}
