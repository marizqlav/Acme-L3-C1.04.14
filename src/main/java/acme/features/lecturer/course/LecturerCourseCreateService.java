
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerCourseRepository repository;

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
		object.setCode(this.newCode(this.repository.findFirstByOrderByCodeDesc().getCode()));
		super.bind(object, "title", "resumen", "retailPrice", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		final SelectChoices choices;

		Tuple tuple;

		tuple = super.unbind(object, "title", "resumen", "retailPrice", "link");

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
