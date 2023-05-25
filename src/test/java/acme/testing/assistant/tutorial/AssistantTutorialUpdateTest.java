
package acme.testing.assistant.tutorial;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialUpdateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String estimatedTime, final String description, final String course) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("estimatedTime", estimatedTime);
		super.fillInputBoxIn("course", course);

		super.clickOnSubmit("Update");

		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("estimatedTime", estimatedTime);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("course", course);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void testNegative(final int recordIndex, final String title, final String estimatedTime, final String description, final String course) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("estimatedTime", estimatedTime);
		super.fillInputBoxIn("course", course);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}
}
