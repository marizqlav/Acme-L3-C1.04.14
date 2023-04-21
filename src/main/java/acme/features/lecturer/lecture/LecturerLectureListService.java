
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lectures.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListService extends AbstractService<Lecturer, Lecture> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerLectureRepository repository;

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
		Collection<Lecture> objects;
		objects = this.repository.findAllLectures();
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findAllLecturesByLecturer(principal.getActiveRoleId());
		super.getBuffer().setData(objects);

	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "resumen", "lectureType", "estimatedTime", "body");

		super.getResponse().setData(tuple);
	}
}
