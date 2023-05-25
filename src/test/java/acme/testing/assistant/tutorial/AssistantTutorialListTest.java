
package acme.testing.assistant.tutorial;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String code) {
		// HINT: this test signs in as an employer, lists all of the jobs, 
		// HINT+ and then checks that the listing shows the expected data.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, code);

		super.signOut();
	}
}
