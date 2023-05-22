
package acme.features.lecturer.lecture;

import java.util.List;
import java.util.stream.Collectors;

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
		object.setDraftmode(true);

		super.bind(object, "title", "resumen", "lectureType", "estimatedTime", "body");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
		final List<String> titulos = this.repository.findAllLectures().stream().map(x -> x.getTitle()).collect(Collectors.toList());
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
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(!titulos.contains(object.getTitle()), "title", "lecturer.lecture.form.titulo.duplicated");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;
		object.setDraftmode(true);

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
