
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
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Course object;
		Lecturer Lecturer;

		Lecturer = this.repository.findLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Course();
		object.setLecturer(Lecturer);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "title", "resumen", "CourseType", "estimatedTime", "body");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		final SelectChoices choices;

		Tuple tuple;

		tuple = super.unbind(object, "title", "resumen", "CourseType", "estimatedTime", "body");

		super.getResponse().setData(tuple);
	}

	//	public String newCode() {
	//
	//		final String lastCode = this.repository.findFirstByOrderByCodeDesc().getCode();
	//
	//		String prefijo = lastCode.substring(0, 3);
	//		final int numeroActual = Integer.parseInt(lastCode.substring(3));
	//		int nuevoNumero = numeroActual + 1;
	//		if (nuevoNumero > 999) {
	//			int indiceLetra = prefijo.charAt(2) - 'A';
	//			if (indiceLetra == 25)
	//				throw new RuntimeException("Se alcanzó el límite de códigos posibles");
	//			indiceLetra++;
	//			final char nuevaLetra = (char) ('A' + indiceLetra);
	//			prefijo = prefijo.substring(0, 2) + nuevaLetra;
	//			nuevoNumero = 0;
	//		}
	//		final String nuevoCodigo = prefijo + String.format("%03d", nuevoNumero);
	//		return nuevoCodigo;
	//	}

}
