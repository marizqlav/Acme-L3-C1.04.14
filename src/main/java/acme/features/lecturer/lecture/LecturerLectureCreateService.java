
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lectures.Lecture;
import acme.entities.lectures.LectureType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

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
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Lecture object;
		Lecturer lecturer;

		lecturer = this.repository.findLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Lecture();
		object.setLecturer(lecturer);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "resumen", "lectureType", "estimatedTime", "body");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("estimatedTime"))
			super.state(object.getEstimatedTime() >= 0.01, "estimatedTime", "lecturer.lecture.form.error.estimatedTime");
		if (!super.getBuffer().getErrors().hasErrors("lectureType"))
			super.state(!object.getLectureType().equals(LectureType.BALANCED), "lectureType", "lecturer.lecture.form.error.lectureType");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;
		object.setDraftmode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		SelectChoices choices;

		Tuple tuple;

		choices = SelectChoices.from(LectureType.class, object.getLectureType());

		tuple = super.unbind(object, "title", "resumen", "lectureType", "estimatedTime", "body");

		tuple.put("lectureTypes", choices);

		super.getResponse().setData(tuple);
	}

}
