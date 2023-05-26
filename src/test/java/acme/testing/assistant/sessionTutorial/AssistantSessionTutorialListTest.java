
package acme.testing.assistant.sessionTutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class AssistantSessionTutorialListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/sessionTutorial/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorial, final int indexRecord, final String title, final String description) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorial);

		super.clickOnButton("Sessions");

		super.checkListingExists();

		super.checkColumnHasValue(indexRecord, 0, title);
		super.checkColumnHasValue(indexRecord, 1, description);

		super.signOut();
	}

	@Order(40)
	@Test
	public void test300Hacking() {

		// This test tries to create an enrolment using principals with
		// inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/assistant/session-tutorial/list");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/assistant/session-tutorial/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/assistant/session-tutorial/list");
		super.checkPanicExists();
		super.signOut();
	}
}
