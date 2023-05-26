
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AssistantService;
import acme.entities.courses.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseUpdateService extends AbstractService<Lecturer, Course> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerCourseRepository	repository;

	@Autowired
	protected AssistantService			assistentService;

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
		Course course;
		Lecturer lecturer;

		id = super.getRequest().getData("id", int.class);
		course = this.repository.findCourseById(id);
		lecturer = course.getLecturer();

		status = course != null && super.getRequest().getPrincipal().hasRole(lecturer) && course.getDraftMode();
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
		final Course course = this.repository.findCourseById(super.getRequest().getData("id", int.class));
		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(this.assistentService.validatePrice(object.getRetailPrice(), 0, 1000000), "retailPrice", "lecturer.course.form.error.retailPrice");
		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(!object.getRetailPrice().toString().contains("-"), "retailPrice", "lecturer.course.form.error.retailPrice.negative");
		if (!super.getRequest().getData("code", String.class).equals(course.getCode()))
			super.state(false, "code", "code.not.edit");
		if (!super.getBuffer().getErrors().hasErrors("draftmode"))
			super.state(object.getDraftMode(), "code", "code.draftmode");

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "resumen", "retailPrice", "link");

		tuple.put("draftmode", object.getDraftMode());

		super.getResponse().setData(tuple);
	}

}
