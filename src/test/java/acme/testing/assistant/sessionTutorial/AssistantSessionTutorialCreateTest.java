
package acme.testing.assistant.sessionTutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.TestHarness;

public class AssistantSessionTutorialCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/sessionTutorial/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorial, final int indexREcord, final String title, final String description, final String type, final String startDate, final String endDate, final String link) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorial);

		super.clickOnButton("Sessions");

		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("sessionType", type);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

		super.checkListingExists();

		super.checkColumnHasValue(indexREcord, 0, title);
		super.clickOnListingRecord(indexREcord);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("sessionType", type);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/sessionTutorial/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int tutorial, final int indexREcord, final String title, final String description, final String type, final String startDate, final String endDate, final String link) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorial);

		super.clickOnButton("Sessions");

		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("sessionType", type);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

		super.checkErrorsExist();
	}

	@Order(40)
	@Test
	public void test300Hacking() {

		// This test tries to create an enrolment using principals with
		// inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/assistant/session-tutorial/create");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/assistant/session-tutorial/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/assistant/session-tutorial/create");
		super.checkPanicExists();
		super.signOut();
	}
}
