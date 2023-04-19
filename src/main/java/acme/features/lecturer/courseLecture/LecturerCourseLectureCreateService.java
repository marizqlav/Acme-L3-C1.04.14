
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
		Integer lectureId;
		Lecture lecture;
		lectureId = super.getRequest().getData("lecture", int.class);
		lecture = this.repository.findLectureById(lectureId);
		super.bind(object, "course");

		object.setLecture(lecture);

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

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		final Collection<Lecture> l = this.repository.findAllLecturesByLecturer(lecturerId);
		//		Iterator<Lecture> iterator;
		//		iterator = l.iterator();
		choices = new SelectChoices();
		//		choices.add("0", "---", object.getLecture() == null);
		//		while (iterator.hasNext()) {
		//			Lecture choice;
		//			choice = iterator.next();
		//			choices.add(String.valueOf(choice.getId()), choice.getTitle(), choice.equals(object.getLecture()));
		//		}

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
