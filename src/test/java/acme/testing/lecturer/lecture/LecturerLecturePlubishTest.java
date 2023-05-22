
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.lectures.Lecture;
import acme.testing.TestHarness;

public class LecturerLecturePlubishTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String resumen, final String estimatedTime, final String body, final String lectureType) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Lecture List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Publish Lecture");
		super.checkNotErrorsExist();
		super.clickOnListingRecord(recordIndex);
		super.checkNotSubmitExists("Publish Lecture");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String resumen, final String estimatedTime, final String body, final String lectureType) {
		// HINT: this test attempts to publish a Practicum that cannot be published, yet.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Lecture List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkNotSubmitExists("Publish Lecture");

		super.signOut();
	}

	@Test
	public void hackingTest() {
		final Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture l : lectures) {
			param = String.format("id=%d", l.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture/publish", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/lecturer/lecture/publish", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/lecture/publish", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

}
