
package acme.testing.assistant.tutorial;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialPublishTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String title, final String estimatedTime, final String description, final String course) {
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);

		super.clickOnSubmit("Publish");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);

		super.checkNotSubmitExists("Publish");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeTest(final int recordIndex, final String title, final String estimatedTime, final String description, final String course) {
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorial List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);

		super.checkNotSubmitExists("Publish");

		super.signOut();
	}
}
