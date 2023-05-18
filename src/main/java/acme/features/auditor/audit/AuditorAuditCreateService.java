package acme.features.auditor.audit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.courses.Course;
import acme.features.codeGeneration.CodeGenerator;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repo;
	@Autowired
	protected CodeGenerator codeGenerator;


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

		super.getBuffer().setData(audit);
	}

	@Override
	public void bind(final Audit audit) {
		assert audit != null;

		Auditor auditor = repo.findAuditor(super.getRequest().getPrincipal().getActiveRoleId());
        audit.setAuditor(auditor);
		String code = codeGenerator.newCode(Audit.class.getSimpleName());
		audit.setCode(code);

		super.bind(audit, "conclusion", "strongPoints", "weakPoints");

		Course course = repo.findCourse(super.getRequest().getData("course", int.class));

		audit.setCourse(course);

	}

	@Override
	public void validate(final Audit audit) {
		assert audit != null;

		if (!super.getBuffer().getErrors().hasErrors("course")) {
			super.state(audit.getCourse() != null, "course", "auditor.audit.form.course.nullError");
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
		SelectChoices choices = SelectChoices.from(courses, "title", audit.getCourse());

        tuple.put("course", choices.getSelected().getKey());
        tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}