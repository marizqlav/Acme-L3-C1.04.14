
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Course;
import acme.testing.TestHarness;

public class LecturerCourseShowTest extends TestHarness {

	@Autowired
	protected LecturerCourseTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String courseType, final String title, final String resumen, final String retailPrice, final String link) {
		// HINT: this test signs in as an employer, lists all of the jobs, click on  
		// HINT+ one of them, and checks that the form has the expected data.

		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "Course List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("courseType", courseType);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("resumen", resumen);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
		super.checkInputBoxHasValue("link", link);

		super.checkButtonExists("Lectures of the course");
		super.clickOnButton("Lectures of the course");
		super.checkListingExists();
		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show an unpublished job by someone who is not the principal.
		// HINT+ a) estando logueado como companyX no poder ver los detalles de una recipe que no sea suyo;

		final Collection<Course> cursos;
		String param;

		cursos = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course c : cursos)
			if (c.getDraftMode()) {
				param = String.format("id=%d", c.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/course/show", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/lecturer/course/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer2", "lecturer2");
				super.request("/lecturer/course/show", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
