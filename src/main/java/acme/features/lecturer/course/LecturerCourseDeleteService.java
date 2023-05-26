
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.courseLectures.CourseLecture;
import acme.entities.courses.Course;
import acme.features.auditor.audit.AuditorAuditRepository;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerCourseRepository	repository;

	@Autowired
	protected AuditorAuditRepository	auditRepo;
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

	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		final Collection<CourseLecture> courseLectures = this.repository.findCLfromCourse(object.getId());
		for (final CourseLecture courseLecture : courseLectures)
			this.repository.delete(courseLecture);

		final Collection<Audit> audits = this.repository.findAuditCourse(object.getId());

		for (final Audit audit : audits) {
			final Collection<AuditingRecord> ar = this.auditRepo.findRecordsFromAudit(audit.getId());
			this.repository.deleteAll(ar);
			this.repository.delete(audit);
		}

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "resumen", "retailPrice", "link");

		tuple.put("draftmode", object.getDraftMode());

		super.getResponse().setData(tuple);
	}
}
