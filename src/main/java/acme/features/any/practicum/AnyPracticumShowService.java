
package acme.features.any.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPracticumShowService extends AbstractService<Any, Practicum> {

	@Autowired
	protected AnyPracticumRepository pracRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Practicum object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.pracRepository.findPracticumById(id);
		super.getResponse().setAuthorised(!object.isDraftMode());
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.pracRepository.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;
		final Tuple tuple;
		//Show the details of the courses in the system, including their lectures and lecturers.findPracticumByCourse
		//Show the details of the practica that they can list, including their companies.
		//		final Practicum practicum = this.pracRepository.findAllPracticum();
		//		tuple = super.unbind(object, "code", "title", "abstractPracticum", "someGoals");
		//		tuple = super.unbind(object, "code", "title", "resumen", "retailPrice", "link");
		//		tuple.put("lecturerusername", lecturer.getUserAccount().getUsername());
		//		tuple.put("lectureralmamater", lecturer.getAlmaMater());
		//		tuple.put("lecturerresume", lecturer.getResume());
		//		tuple.put("lecturerqualifications", lecturer.getQualifications());
		//		tuple.put("lecturerlink", lecturer.getLink());

		//		super.getResponse().setData(tuple);
	}

}
