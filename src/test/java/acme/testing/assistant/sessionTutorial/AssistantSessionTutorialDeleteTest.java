
package acme.testing.assistant.sessionTutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class AssistantSessionTutorialDeleteTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/sessionTutorial/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Positive(final int tutorial, final int indexREcord, final String title, final String description, final String type, final String startDate, final String endDate, final String link) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorial);

		super.clickOnButton("Sessions");

		super.checkListingExists();

		super.checkColumnHasValue(indexREcord, 0, title);
		super.clickOnListingRecord(indexREcord);

		super.clickOnSubmit("Delete");

		super.checkListingExists();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/sessionTutorial/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Negative(final int tutorial, final int indexREcord, final String title, final String description, final String type, final String startDate, final String endDate, final String link) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorial);

		super.clickOnButton("Sessions");

		super.checkListingExists();

		super.checkColumnHasValue(indexREcord, 0, title);
		super.clickOnListingRecord(indexREcord);

		super.checkNotSubmitExists("Delete");

		super.signOut();
	}

	@Order(40)
	@Test
	public void test300Hacking() {

		// This test tries to create an enrolment using principals with
		// inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/assistant/session-tutorial/delete");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/assistant/session-tutorial/delete");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/assistant/session-tutorial/delete");
		super.checkPanicExists();
		super.signOut();
	}
}
