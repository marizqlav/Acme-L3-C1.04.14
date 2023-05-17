
package acme.testing.lecturer.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseListByLecturerTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/list-by-lecturer-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title) {
		// HINT: this test signs in as an employer, lists all of the jobs, 
		// HINT+ and then checks that the listing shows the expected data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Course List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list all of the practicums using 
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course/list");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/lecturer/course/list");
		super.checkPanicExists();
		super.signOut();

	}

}
