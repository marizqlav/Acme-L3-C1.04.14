
package acme.testing.company.sessionPracticum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanySessionPracticumPublishTest extends TestHarness {

	@Autowired
	protected CompanySessionPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum) {
		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("List Session Practicum");

		super.sortListing(0, "asc");
		super.checkColumnHasValue(sesionRecordIndex, 0, title);
		super.checkColumnHasValue(sesionRecordIndex, 1, abstractSessionPracticum);

		super.clickOnListingRecord(sesionRecordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();
		super.clickOnListingRecord(sesionRecordIndex);
		super.checkFormExists();
		super.checkNotButtonExists("Publish");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test000Negative(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("List Session Practicum");

		super.sortListing(0, "asc");
		super.checkColumnHasValue(sesionRecordIndex, 0, title);
		super.checkColumnHasValue(sesionRecordIndex, 1, abstractSessionPracticum);

		super.clickOnListingRecord(sesionRecordIndex);
		super.checkFormExists();
		super.checkNotSubmitExists("Publish");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.request("/company/session-practicum/publish");
		super.checkPanicExists();

		super.signIn("lecturer1", "lecturer1");
		super.request("/company/session-practicum/publish");
		super.checkPanicExists();
		super.signOut();
	}

}
