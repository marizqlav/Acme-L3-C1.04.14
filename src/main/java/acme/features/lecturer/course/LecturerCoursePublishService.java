
package acme.features.lecturer.course;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.entities.lectures.LectureType;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerCourseRepository repository;

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
	public void validate(final Course object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice() != null, "retailPrice", "lecturer.course.form.error.retailPrice");
		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(!object.getRetailPrice().toString().contains("-"), "retailPrice", "lecturer.course.form.error.retailPrice.negative");

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

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		final Collection<Lecture> cl = this.repository.findAllLecturesByCourse(object.getId());
		final List<Boolean> lb = cl.stream().map(x -> x.getDraftmode()).collect(Collectors.toList());
		if (!lb.contains(true) && !lb.isEmpty())
			object.setDraftMode(false);
		else
			object.setDraftMode(true);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		final String systemCurrency = this.repository.findSystemConfiguration().getSystemCurrency();
		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "resumen", "retailPrice", "link");
		tuple.put("courseType", this.courseType(this.repository.findAllLecturesByCourse(object.getId())));
		tuple.put("draftmode", object.getDraftMode());

		super.getResponse().setData(tuple);
	}

	public LectureType courseType(final Collection<Lecture> lecturesCourse) {
		int theory = 0;
		int handsOn = 0;
		LectureType res = LectureType.THEORY;
		for (final Lecture l : lecturesCourse)
			if (l.getLectureType().equals(LectureType.THEORY))
				theory += 1;
			else if (l.getLectureType().equals(LectureType.HANDSON))
				handsOn += 1;
		if (theory < handsOn)
			res = LectureType.HANDSON;
		else if (theory == handsOn)
			res = LectureType.BALANCED;

		return res;
	}

}
