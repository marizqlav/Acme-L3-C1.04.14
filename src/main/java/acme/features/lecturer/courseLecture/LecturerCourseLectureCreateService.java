
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courseLectures.CourseLecture;
import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
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
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);

		super.getResponse().setAuthorised(status);
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
		Integer lectureId;
		Lecture lecture;
		final int id = super.getRequest().getData("id", int.class);
		final Course c = this.repository.findCourseById(id);
		lectureId = super.getRequest().getData("lecture", int.class);
		lecture = this.repository.findLectureById(lectureId);
		super.bind(object, "course");
		object.setLecture(lecture);
		object.setCourse(c);

	}

	@Override
	public void validate(final CourseLecture object) {
		final int courseid = super.getRequest().getData("id", int.class);
		final Collection<Lecture> lc = this.repository.findLecturesFromCourseLecture(courseid);
		final int lectureId = super.getRequest().getData("lecture", int.class);
		final Lecture lecture = this.repository.findLectureById(lectureId);
		if (!super.getBuffer().getErrors().hasErrors("lecture"))
			super.state(!lc.contains(lecture), "lecture", "lecturer.course-lecture.form.error.lecture");
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

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		final Collection<Lecture> l = this.repository.findAllLecturesByLecturer(lecturerId);
		choices = new SelectChoices();

		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findCourseById(courseId);
		choices = SelectChoices.from(this.repository.findAllLecturesByLecturer(lecturerId), "title", object.getLecture());

		tuple = super.unbind(object, "course");
		tuple.put("lectures", choices);
		tuple.put("lecture", choices.getSelected().getKey());
		tuple.put("id", courseId);
		tuple.put("courseTitle", course.getTitle());
		super.getResponse().setData(tuple);

	}

}
