
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Course;
import acme.testing.TestHarness;

public class LecturerCoursePublishTest extends TestHarness {

	@Autowired
	protected LecturerCourseTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String title, final String lectures) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Course List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Add lecture");
		super.checkFormExists();
		super.fillInputBoxIn("lecture", lectures);
		super.clickOnSubmit("Add lecture");
		super.clickOnSubmit("Publish Course");
		super.clickOnListingRecord(recordIndex);
		super.checkNotSubmitExists("Publish course");

		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeButtonTest(final int recordIndex, final String title, final String lectures) {
		// HINT: this test attempts to publish a Practicum that cannot be published, yet.
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Course List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkNotSubmitExists("Publish course");

		super.checkNotErrorsExist();

		super.signOut();
	}

	@Test
	public void hackingTest() {
		Collection<Course> courses;
		String param;

		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course c : courses) {
			param = String.format("id=%d", c.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/course/publish", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/lecturer/course/publish", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/course/publish", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

}
