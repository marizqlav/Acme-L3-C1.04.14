
package acme.features.lecturer.courseLecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courseLectures.CourseLecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureDeleteService extends AbstractService<Lecturer, CourseLecture> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerCourseLectureRepository repository;

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
		final CourseLecture cl;

		id = super.getRequest().getData("id", int.class);
		cl = this.repository.findCourseLectureById(id);
		status = cl != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CourseLecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CourseLecture object) {
		super.bind(object, "id");

	}

	@Override
	public void validate(final CourseLecture object) {

	}

	@Override
	public void perform(final CourseLecture object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;

		final Tuple tuple;
		tuple = super.unbind(object, "id");
		tuple.put("course", object.getCourse().getTitle());
		tuple.put("lecture", object.getLecture().getTitle());
		super.getResponse().setData(tuple);

	}

}
