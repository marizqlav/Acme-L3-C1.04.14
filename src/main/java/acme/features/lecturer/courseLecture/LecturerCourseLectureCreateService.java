
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

		super.bind(object, "lecture");
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

		final SelectChoices choices;

		Tuple tuple;

		//		choices = SelectChoices.from(LectureType.class, object.getLectureType());

		tuple = super.unbind(object, "lecture");

		tuple.put("id", object.getId());
		//		tuple.put("lectureTypes", choices);

		super.getResponse().setData(tuple);
	}

}
