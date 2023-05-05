
package acme.testing.company.sessionPracticum;

import java.util.Collection;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.Practicum;
import acme.testing.TestHarness;

public class CompanySessionPracticumPublishTest extends TestHarness {

	@Autowired
	protected CompanySessionPracticumTestRepository repository;


	public void positiveTest(final int recordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link, final String practicumTitle) {
		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abstractSessionPracticum);
		super.checkColumnHasValue(recordIndex, 2, practicumTitle);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractSessionPracticum", abstractSessionPracticum);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("link", link);
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.clickOnListingRecord(recordIndex);
		super.checkNotSubmitExists("Publish");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeButtonTest(final int recordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link) {
		// HINT: this test attempts to publish a Practicum that cannot be published, yet.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abstractSessionPracticum);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractSessionPracticum", abstractSessionPracticum);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("link", link);
		super.clickOnSubmit("Publish");
		super.checkNotPanicExists();

		super.signOut();

	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to publish a Practicum with a role other than "Employer".

		final Collection<Practicum> practicums;
		String params;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practicums)
			if (practicum.getDraftMode()) {
				params = String.format("id=%d", practicum.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/session-practicum/publish", params);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/company/session-practicum/publish", params);
				super.checkPanicExists();
				super.signOut();

			}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to publish a published Practicum that was registered by the principal.

		Collection<Practicum> practicums;
		String params;

		super.signIn("company1", "company1");
		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practicums)
			if (!practicum.getDraftMode()) {
				params = String.format("id=%d", practicum.getId());
				super.request("/company/session-practicum/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to publish a Practicum that wasn't registered by the principal,
		// HINT+ be it published or unpublished.

		Collection<Practicum> practicums;
		String params;

		super.signIn("company2", "company2");
		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practicums) {
			params = String.format("id=%d", practicum.getId());
			super.request("/company/session-practicum/publish", params);
		}
		super.signOut();
	}

}
