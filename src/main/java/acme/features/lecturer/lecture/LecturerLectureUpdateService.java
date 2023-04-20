
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
public class LecturerLectureUpdateService extends AbstractService<Lecturer, Lecture> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerLectureRepository repository;

	//AbstractServiceInterface -------------------------------


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
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findLectureById(id);

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
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(!object.getTitle().isEmpty(), "title", "lecturer.lecture.form.error.title");
		if (!super.getBuffer().getErrors().hasErrors("resumen"))
			super.state(!object.getResumen().isEmpty(), "resumen", "lecturer.lecture.form.error.resumen");
		if (!super.getBuffer().getErrors().hasErrors("body"))
			super.state(!object.getBody().isEmpty(), "body", "lecturer.lecture.form.error.body");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		SelectChoices choices;

		Tuple tuple;

		choices = SelectChoices.from(LectureType.class, object.getLectureType());

		tuple = super.unbind(object, "title", "resumen", "lectureType", "estimatedTime", "body");

		tuple.put("id", object.getId());
		tuple.put("lectureTypes", choices);

		super.getResponse().setData(tuple);
	}

}
