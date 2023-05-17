
package acme.testing.company.sessionPracticum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class CompanySessionPracticumDeleteTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link) {

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
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractSessionPracticum", abstractSessionPracticum);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("link", link);

		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
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
		super.clickOnListingRecord(sesionRecordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractSessionPracticum", abstractSessionPracticum);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("link", link);

		super.checkNotSubmitExists("Delete");

		super.signOut();
	}

}
