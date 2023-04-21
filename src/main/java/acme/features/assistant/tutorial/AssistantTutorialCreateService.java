
package acme.features.assistant.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

	// Internal State ------------------------------------------
	@Autowired
	protected AssistantTutorialRepository	repository;

	@Autowired
	protected LecturerCourseRepository		courseRepo;

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
		Tutorial object;
		Assistant assistant;

		assistant = this.repository.findAssistantById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Tutorial();
		object.setAssistant(assistant);

		super.getBuffer().setData(object);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("estimatedTime"))
			super.state(object.getEstimatedTime() >= 0.01, "estimatedTime", "assistant.tutorial.form.error.estimatedTime");
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;
		object.setDraftMode(true);
		this.repository.save(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;
		final int id = super.getRequest().getData("course", int.class);
		object.setCourse(this.courseRepo.findCourseById(id));
		object.setCode(this.newCode(this.repository.findFirstByOrderByCodeDesc().getCode()));
		object.setAssistant(this.repository.findAssistantById(super.getRequest().getPrincipal().getActiveRoleId()));
		super.bind(object, "title", "description", "estimatedTime");
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;

		choices = new SelectChoices();

		choices = SelectChoices.from(this.courseRepo.findAllCourses(), "title", object.getCourse());

		tuple = super.unbind(object, "title", "description", "estimatedTime");
		tuple.put("courses", choices);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("assistant", this.repository.findAssistantById(super.getRequest().getPrincipal().getActiveRoleId()));
		super.getResponse().setData(tuple);
	}

	public String newCode(final String lastCode) {

		String prefijo = lastCode.substring(0, 3);
		final int numeroActual = Integer.parseInt(lastCode.substring(3));
		int nuevoNumero = numeroActual + 1;
		if (nuevoNumero > 999) {
			int indiceLetra = prefijo.charAt(2) - 'A';
			if (indiceLetra == 25)
				throw new RuntimeException("Se alcanzó el límite de códigos posibles");
			indiceLetra++;
			final char nuevaLetra = (char) ('A' + indiceLetra);
			prefijo = prefijo.substring(0, 2) + nuevaLetra;
			nuevoNumero = 0;
		}
		final String nuevoCodigo = prefijo + String.format("%03d", nuevoNumero);
		return nuevoCodigo;
	}
}
