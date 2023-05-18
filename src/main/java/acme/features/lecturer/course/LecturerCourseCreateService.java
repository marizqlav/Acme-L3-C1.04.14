
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.AssistantService;
import acme.entities.courses.Course;
import acme.features.codeGeneration.CodeGenerator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerCourseRepository	repository;

	@Autowired
	protected AssistantService			assistentService;

	@Autowired
	protected CodeGenerator				codeGenerator;

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
		Course object;
		Lecturer Lecturer;

		Lecturer = this.repository.findLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Course();
		object.setLecturer(Lecturer);
		object.setDraftMode(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		object.setCode(this.codeGenerator.newCode(Course.class.getSimpleName()));
		super.bind(object, "title", "resumen", "retailPrice", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(this.assistentService.validatePrice(object.getRetailPrice(), 0, 1000000), "retailPrice", "lecturer.course.form.error.retailPrice");
		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(!object.getRetailPrice().toString().contains("-"), "retailPrice", "lecturer.course.form.error.retailPrice.negative");

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

		tuple = super.unbind(object, "title", "resumen", "retailPrice", "link");

		super.getResponse().setData(tuple);
	}

}
