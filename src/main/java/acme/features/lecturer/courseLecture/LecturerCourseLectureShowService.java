
package acme.features.lecturer.courseLecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courseLectures.CourseLecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureShowService extends AbstractService<Lecturer, CourseLecture> {

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
		final boolean status;

		final CourseLecture cl = this.repository.findCourseLectureById(super.getRequest().getData("id", int.class));
		status = cl != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CourseLecture object;

		object = this.repository.findCourseLectureById(super.getRequest().getData("id", int.class));

		super.getBuffer().setData(object);
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
