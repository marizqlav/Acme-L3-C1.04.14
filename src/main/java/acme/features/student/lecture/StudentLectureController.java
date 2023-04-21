
package acme.features.student.lecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.lectures.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentLectureController extends AbstractController<Student, Lecture> {

	@Autowired
	StudentLectureListService	listService;

	@Autowired
	StudentLectureShowService	showService;


	@PostConstruct
	void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
