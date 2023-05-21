
package acme.testing.company.practicum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanyPracticumDeleteTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstractPracticum, final String someGoals) {

		super.signIn("company1", "company1");
		super.clickOnMenu("Company", "Practicum list");

		super.checkListingExists();
		super.sortListing(0, "desc");
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

	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/company/practicum/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void test200Negative(final int recordIndex, final String code, final String title, final String abstractPracticum, final String someGoals) {
	//
	//		super.signIn("company1", "company1");
	//
	//		super.clickOnMenu("Company", "Practicum list");
	//		super.checkListingExists();
	//
	//		super.sortListing(0, "asc");
	//		super.checkColumnHasValue(recordIndex, 0, code);
	//		super.checkColumnHasValue(recordIndex, 1, title);
	//		super.clickOnListingRecord(recordIndex);
	//
	//		super.checkInputBoxHasValue("code", code);
	//		super.checkInputBoxHasValue("title", title);
	//		super.checkInputBoxHasValue("abstractPracticum", abstractPracticum);
	//		super.checkInputBoxHasValue("someGoals", someGoals);
	//
	//		super.checkNotSubmitExists("Delete");
	//
	//		super.signOut();
	//	}

	//	@Test
	//	public void test300Hacking() {
	//		// HINT: this test tries to show an unpublished job by someone who is not the principal.
	//		// HINT+ a) estando logueado como companyX no poder ver los detalles de una recipe que no sea suyo;
	//
	//		Collection<Practicum> practicums;
	//		String param;
	//
	//		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
	//		for (final Practicum practicum : practicums)
	//			if (practicum.isDraftMode()) {
	//				param = String.format("id=%d", practicum.getId());
	//
	//				super.checkLinkExists("Sign in");
	//				super.request("/company/practicum/delete", param);
	//				super.checkPanicExists();
	//
	//				super.signIn("administrator1", "administrator1");
	//				super.request("/company/practicum/delete", param);
	//				super.checkPanicExists();
	//				super.signOut();
	//
	//				super.signIn("company2", "company2");
	//				super.request("/company/practicum/delete", param);
	//				super.checkPanicExists();
	//				super.signOut();
	//
	//			}
	//	}

}
