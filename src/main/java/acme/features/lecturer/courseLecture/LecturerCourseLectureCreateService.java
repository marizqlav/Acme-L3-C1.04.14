
package acme.features.lecturer.courseLecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courseLectures.CourseLecture;
import acme.entities.courses.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureCreateService extends AbstractService<Lecturer, CourseLecture> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerCourseLectureRepository repository;

	//AbstractServiceInterface -------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CourseLecture object;
		Course course;

		course = this.repository.findCourseById(super.getRequest().getData("id", int.class));
		object = new CourseLecture();
		object.setCourse(course);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CourseLecture object) {
		assert object != null;

		super.bind(object, "course", "lecture");
	}

	@Override
	public void validate(final CourseLecture object) {

	}

	@Override
	public void perform(final CourseLecture object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;

		Tuple tuple;
		int courseId;
		int lecturerId;
		Course course;
		SelectChoices choices;

		courseId = super.getRequest().getData("courseId", int.class);
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		course = this.repository.findCourseById(courseId);
		choices = SelectChoices.from(this.repository.findLecturesAvailableForACourse(courseId, lecturerId), "title", object.getLecture());

		tuple = super.unbind(object, "course", "lecture");
		tuple.put("lectures", choices);
		tuple.put("lecture", choices.getSelected().getKey());
		tuple.put("id", courseId);
		tuple.put("courseTitle", course.getTitle());
		super.getResponse().setData(tuple);
	}

}
