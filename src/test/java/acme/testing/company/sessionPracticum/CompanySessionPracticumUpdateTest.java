
package acme.testing.company.sessionPracticum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanySessionPracticumUpdateTest extends TestHarness {

	@Autowired
	protected CompanySessionPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link) {
		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List Session Practicum");

		super.checkListingExists();
		super.sortListing(2, "asc");
		super.clickOnListingRecord(sesionRecordIndex);

		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractSessionPracticum", abstractSessionPracticum);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");
		super.clickOnListingRecord(sesionRecordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractSessionPracticum", abstractSessionPracticum);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List Session Practicum");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(sesionRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractSessionPracticum", abstractSessionPracticum);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");
		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.request("/practicum/session-practicum/update");
		super.checkPanicExists();

		super.signIn("company1", "company1");
		super.request("/practicum/session-practicum/update");
		super.checkPanicExists();
		super.signOut();
	}

}
