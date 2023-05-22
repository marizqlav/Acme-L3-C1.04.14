
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.lectures.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureDeleteTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Positive(final int recordIndex, final String title, final String resumen, final String estimatedTime, final String body, final String lectureType) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Lecture List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("resumen", resumen);
		super.checkInputBoxHasValue("estimatedTime", estimatedTime);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("lectureType", lectureType);

		super.clickOnSubmit("Delete Lecture");

		super.checkListingExists();
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Negative(final int recordIndex, final String title, final String resumen, final String estimatedTime, final String body, final String lectureType) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Lecture List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("resumen", resumen);
		super.checkInputBoxHasValue("estimatedTime", estimatedTime);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("lectureType", lectureType);

		super.checkNotSubmitExists("Delete Lecture");

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
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();
			//comentario
			super.signIn("administrator1", "administrator1");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

}
