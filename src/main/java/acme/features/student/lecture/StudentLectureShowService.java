
package acme.features.student.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lectures.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentLectureShowService extends AbstractService<Student, Lecture> {

	@Autowired
	protected StudentLectureRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final Lecture lecture;
		int id;

		id = super.getRequest().getData("id", int.class);
		lecture = this.repo.findLectureById(id);

		super.getBuffer().setData(lecture);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "resumen", "lectureType", "estimatedTime", "body");

		super.getResponse().setData(tuple);
	}

}
