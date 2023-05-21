
package acme.testing.company.practicum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanyPracticumUpdateTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstractPracticum, final String someGoals) {
		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractPracticum", abstractPracticum);
		super.fillInputBoxIn("someGoals", someGoals);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractPracticum", abstractPracticum);
		super.checkInputBoxHasValue("someGoals", someGoals);

		super.signOut();

	}

	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/company/practicum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	@Order(20)
	//	public void test200Negative(final int recordIndex, final String title, final String abstractPracticum, final String someGoals) {
	//		super.signIn("company1", "company1");
	//
	//		super.clickOnMenu("Company", "Practicum list");
	//		super.checkListingExists();
	//		super.sortListing(0, "asc");
	//		super.clickOnListingRecord(recordIndex);
	//		super.checkFormExists();
	//
	//		super.checkFormExists();
	//		super.fillInputBoxIn("title", title);
	//		super.fillInputBoxIn("abstractPracticum", abstractPracticum);
	//		super.fillInputBoxIn("someGoals", someGoals);
	//		super.clickOnSubmit("Update");
	//
	//		super.checkErrorsExist();
	//
	//		super.signOut();
	//	}
	//
	//	@Test
	//	public void test300Hacking() {
	//		Collection<Practicum> practicums;
	//		String param;
	//
	//		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
	//		for (final Practicum practicum : practicums) {
	//			param = String.format("id=%d", practicum.getId());
	//
	//			super.checkLinkExists("Sign in");
	//			super.request("/company/practicum/update", param);
	//			super.checkPanicExists();
	//
	//			super.signIn("administrator1", "administrator1");
	//			super.request("/company/practicum/update", param);
	//			super.checkPanicExists();
	//			super.signOut();
	//
	//			super.signIn("company2", "company2");
	//			super.request("/company/practicum/update", param);
	//			super.checkPanicExists();
	//			super.signOut();
	//
	//		}
	//	}

}
