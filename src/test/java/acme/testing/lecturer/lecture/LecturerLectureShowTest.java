
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.lectures.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureShowTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;

	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/lecturer/lecture/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void test100Positive(final int recordIndex, final String title, final String resumen, final String estimatedTime, final String body, final String lectureType) {
	//		// HINT: this test signs in as an employer, lists all of the jobs, click on  
	//		// HINT+ one of them, and checks that the form has the expected data.
	//
	//		super.signIn("lecturer1", "lecturer1");
	//		super.clickOnMenu("Lecturer", "Lecture List");
	//		super.checkListingExists();
	//		super.sortListing(0, "asc");
	//
	//		super.checkColumnHasValue(recordIndex, 0, title);
	//		super.clickOnListingRecord(recordIndex);
	//
	//		super.checkFormExists();
	//		super.checkInputBoxHasValue("title", title);
	//		super.checkInputBoxHasValue("resumen", resumen);
	//		super.checkInputBoxHasValue("estimatedTime", estimatedTime);
	//		super.checkInputBoxHasValue("body", body);
	//		super.checkInputBoxHasValue("lectureType", lectureType);
	//		super.signOut();
	//	}


	@Test
	public void test200Negative() {
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show an unpublished job by someone who is not the principal.
		// HINT+ a) estando logueado como companyX no poder ver los detalles de una recipe que no sea suyo;

		final Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture l : lectures)
			if (l.getDraftmode()) {
				param = String.format("id=%d", l.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture/show", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/lecturer/lecture/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer2", "lecturer2");
				super.request("/lecturer/lecture/show", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
