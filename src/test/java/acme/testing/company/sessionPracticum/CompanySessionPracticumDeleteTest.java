
package acme.testing.company.sessionPracticum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.sessionPracticum.SessionPracticum;
import acme.testing.TestHarness;

public class CompanySessionPracticumDeleteTest extends TestHarness {

	@Autowired
	protected CompanySessionPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link) {

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
	public void test000Negative(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link) {

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

	@Test
	public void test300Hacking() {
		Collection<SessionPracticum> sessionPracticums;
		String param;

		sessionPracticums = this.repository.findManySesionsPracticumByCompanyUsername("company1");
		for (final SessionPracticum sessionPracticum : sessionPracticums)
			if (sessionPracticum.isDraftModeSession()) {
				param = String.format("id=%d", sessionPracticum.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/session-practicum/delete", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/company/session-practicum/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company2", "company2");
				super.request("/company/session-practicum/delete", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
