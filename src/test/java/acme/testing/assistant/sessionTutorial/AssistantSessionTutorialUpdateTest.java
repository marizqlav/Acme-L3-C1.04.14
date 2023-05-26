
package acme.testing.assistant.sessionTutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.SessionTutorial;
import acme.testing.TestHarness;
import acme.testing.assistant.tutorial.AssistantTutorialTestRepository;

public class AssistantSessionTutorialUpdateTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/sessionTutorial/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorial, final int indexREcord, final String title, final String description, final String type, final String startDate, final String endDate, final String link) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorial);

		super.clickOnButton("Sessions");

		super.checkListingExists();

		super.clickOnListingRecord(indexREcord);

		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("sessionType", type);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.clickOnListingRecord(indexREcord);

		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("sessionType", type);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/sessionTutorial/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int tutorial, final int indexREcord, final String title, final String description, final String type, final String startDate, final String endDate, final String link) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorial);

		super.clickOnButton("Sessions");

		super.checkListingExists();

		super.clickOnListingRecord(indexREcord);

		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("sessionType", type);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");
		super.checkErrorsExist();
	}

	@Test
	public void test300Hacking() {

		// HINT: this test tries to show an unpublished enrolment by someone who is not the principal.

		Collection<SessionTutorial> sessions;
		String param;

		sessions = this.repository.findManySessionsByAssistantUsername("assistant1");
		for (final SessionTutorial session : sessions)
			if (session.isDraftMode()) {
				param = String.format("id=%d", session.getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/session-tutorial/show", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/assistant/session-tutorial/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant2", "assistant2");
				super.request("/assistant/session-tutorial/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/assistant/session-tutorial/show", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
