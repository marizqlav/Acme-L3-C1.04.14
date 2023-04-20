
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerCourseRepository repository;

	//AbstractServiceInterface -------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		final Course course;

		id = super.getRequest().getData("id", int.class);
		course = this.repository.findCourseById(id);
		status = course != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "title", "resumen", "retailPrice", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "resumen", "retailPrice", "link");

		super.getResponse().setData(tuple);
	}
}
