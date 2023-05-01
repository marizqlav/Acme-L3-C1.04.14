
package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class CompanyPracticumCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	@Order(10)
	public void positiveTest(final int recordIndex, final String code, final String title, final String abstractPracticum, final String someGoals) {

		super.signIn("company1", "company1");
		super.clickOnMenu("Company", "Practicum list");

		super.checkListingExists();
		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractPracticum", abstractPracticum);
		super.fillInputBoxIn("someGoals", someGoals);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Company\", \"Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractPracticum", abstractPracticum);
		super.checkInputBoxHasValue("someGoals", someGoals);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	@Order(20)
	public void negativeTest(final int recordIndex, final String code, final String title, final String abstractPracticum, final String someGoals) {

		super.signIn("company1", "company1");
		super.clickOnMenu("Company", "Practicum list");
		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractPracticum", abstractPracticum);
		super.fillInputBoxIn("someGoals", someGoals);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();
		super.signOut();
	}

	//	@Order(30)
	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a job using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/company/practicum/create");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/company/practicum/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("worker1", "worker1");
		super.request("/company/practicum/create");
		super.checkPanicExists();
		super.signOut();
	}

}
