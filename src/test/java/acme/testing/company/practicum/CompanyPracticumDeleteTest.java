
package acme.testing.company.practicum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class CompanyPracticumDeleteTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String code, final String title, final String abstractPracticum, final String someGoals) {

		super.signIn("company1", "company1");
		super.clickOnMenu("Company", "Practicum list");

		super.checkListingExists();
		super.sortListing(1, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractPracticum", abstractPracticum);
		super.checkInputBoxHasValue("someGoals", someGoals);
		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.signOut();
	}
}
