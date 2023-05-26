
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialShowTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;

	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/assistant/tutorial/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void test100Positive(final int recordIndex, final String title, final String estimatedTime, final String description, final String course) {
	//
	//		super.signIn("assistant1", "assistant1");
	//		super.clickOnMenu("Assistant", "Tutorial List");
	//		super.checkListingExists();
	//		super.sortListing(0, "asc");
	//
	//		super.checkColumnHasValue(recordIndex, 0, title);
	//		super.clickOnListingRecord(recordIndex);
	//
	//		super.checkFormExists();
	//		super.checkInputBoxHasValue("title", title);
	//		super.checkInputBoxHasValue("estimatedTime", estimatedTime);
	//		super.checkInputBoxHasValue("description", description);
	//		super.checkInputBoxHasValue("course", course);
	//		super.signOut();
	//	}


	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

		// HINT: this test tries to show an unpublished enrolment by someone who is not the principal.

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials)
			if (tutorial.getDraftMode()) {
				param = String.format("id=%d", tutorial.getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/tutorial/show", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/assistant/tutorial/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant2", "assistant2");
				super.request("/assistant/tutorial/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/assistant/tutorial/show", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
