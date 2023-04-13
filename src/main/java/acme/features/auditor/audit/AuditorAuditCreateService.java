package acme.features.auditor.audit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.courses.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repo;


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
		Audit audit = new Audit();

        Auditor auditor = repo.findAuditor(super.getRequest().getPrincipal().getActiveRoleId());
        audit.setAuditor(auditor);

		super.getBuffer().setData(audit);
	}

	@Override
	public void bind(final Audit audit) {
		assert audit != null;

		super.bind(audit, "code", "conclusion", "strongPoints", "weakPoints");

		//Course course = super.getRequest().getData("course", Course.class);
		Course course = repo.findCourse(super.getRequest().getData("courseId", int.class));
        audit.setCourse(course);

	}

	@Override
	public void validate(final Audit audit) {
		assert audit != null;

		if (!super.getBuffer().getErrors().hasErrors("course")) {
			super.state(audit.getCourse() != null, "courseId", "auditor.audit.form.course.nullError");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			super.state(repo.findByCode(audit.getCode()) == null, "code", "auditor.audit.form.code.repeated");
		}

    }

	@Override
	public void perform(final Audit audit) {
		assert audit != null;
		repo.save(audit);
	}

	@Override
	public void unbind(final Audit audit) {
		assert audit != null;

		Tuple tuple;
		
		tuple = super.unbind(audit, "code", "conclusion", "strongPoints", "weakPoints");

		List<Course> courses = repo.findAllCourses();

        tuple.put("courses", courses);

		super.getResponse().setData(tuple);
	}
}