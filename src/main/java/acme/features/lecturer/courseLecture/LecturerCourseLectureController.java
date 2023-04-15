
package acme.features.lecturer.courseLecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.courseLectures.CourseLecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerCourseLectureController extends AbstractController<Lecturer, CourseLecture> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureCreateService createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {

		super.addBasicCommand("create", this.createService);
	}

}
