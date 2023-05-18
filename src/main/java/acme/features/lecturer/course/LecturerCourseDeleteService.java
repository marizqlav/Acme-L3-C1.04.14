
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.courseLectures.CourseLecture;
import acme.entities.courses.Course;
import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.features.auditor.audit.AuditorAuditRepository;
import acme.features.company.practicum.CompanyPracticumRepository;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerCourseRepository		repository;

	@Autowired
	protected CompanyPracticumRepository	practRepo;

	@Autowired
	protected AuditorAuditRepository		auditRepo;
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
		final Course course;

		id = super.getRequest().getData("id", int.class);
		course = this.repository.findCourseById(id);
		status = course != null;

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
		final Collection<Practicum> practicum = this.repository.findPracticumCourse(object.getId());

		for (final Practicum p : practicum) {
			Collection<SessionPracticum> practicumSessions;
			practicumSessions = this.practRepo.findSessionPracticumByPracticumId(p.getId());
			this.repository.deleteAll(practicumSessions);
			this.repository.delete(p);
		}
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

		super.getResponse().setData(tuple);
	}
}
