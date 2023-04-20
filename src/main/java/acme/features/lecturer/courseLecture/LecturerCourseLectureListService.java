
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courseLectures.CourseLecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureListService extends AbstractService<Lecturer, CourseLecture> {

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
		Collection<CourseLecture> object;

		object = this.repository.findCourseLectureByCourseId(super.getRequest().getData("courseId", int.class));

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
