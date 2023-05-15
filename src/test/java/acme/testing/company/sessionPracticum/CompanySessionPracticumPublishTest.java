
package acme.testing.company.sessionPracticum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanySessionPracticumPublishTest extends TestHarness {

	@Autowired
	protected CompanySessionPracticumTestRepository repository;

	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/company/session-practicum/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void positiveTest(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum) {
	//		super.signIn("company1", "company1");
	//
	//		super.clickOnMenu("Company", "Practicum list");
	//		super.checkListingExists();
	//		super.sortListing(0, "asc");
	//
	//		super.clickOnListingRecord(recordIndex);
	//		super.checkFormExists();
	//		super.clickOnButton("List Session Practicum");
	//
	//		super.sortListing(0, "asc");
	//		super.checkColumnHasValue(sesionRecordIndex, 0, title);
	//		super.checkColumnHasValue(sesionRecordIndex, 1, abstractSessionPracticum);
	//
	//		super.clickOnListingRecord(sesionRecordIndex);
	//		super.checkFormExists();
	//		super.clickOnSubmit("Publish");
	//		super.checkNotErrorsExist();
	//
	//		super.signOut();
	//	}
	//
	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/company/session-practicum/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void negativeButtonTest(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum) {
	//		// HINT: this test attempts to publish a Practicum that cannot be published, yet.
	//
	//		super.signIn("company1", "company1");
	//
	//		super.clickOnMenu("Company", "Practicum list");
	//		super.checkListingExists();
	//		super.sortListing(0, "asc");
	//
	//		super.clickOnListingRecord(recordIndex);
	//		super.checkFormExists();
	//		super.clickOnButton("List Session Practicum");
	//
	//		super.sortListing(0, "asc");
	//		super.checkColumnHasValue(sesionRecordIndex, 0, title);
	//		super.checkColumnHasValue(sesionRecordIndex, 1, abstractSessionPracticum);
	//
	//		super.clickOnListingRecord(recordIndex);
	//		super.checkFormExists();
	//		super.checkNotSubmitExists("Publish");
	//
	//		super.signOut();
	//	}


	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/publish-negative2.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link) {
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

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractSessionPracticum", abstractSessionPracticum);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Publish");

		super.checkErrorsExist();
		super.signOut();
	}

	//	@Test
	//	public void hackingTest() {
	//		super.request("/company/session-practicum/publish");
	//		super.checkPanicExists();
	//
	//		super.signIn("company1", "company1");
	//		super.request("/company/session-practicum/publish");
	//		super.checkPanicExists();
	//		super.signOut();
	//	}

}
