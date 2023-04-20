package acme.features.auditor.audit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.courses.Course;
import acme.features.CodeGenerator;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

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
		System.out.println("load");
		Audit audit = new Audit();

        Auditor auditor = repo.findAuditor(super.getRequest().getPrincipal().getActiveRoleId());
        audit.setAuditor(auditor);
		String code = CodeGenerator.newCode(repo.findFirstByOrderByCodeDesc().getCode());
		audit.setCode(code);

		super.getBuffer().setData(audit);
	}

	@Override
	public void bind(final Audit audit) {
		assert audit != null;

		super.bind(audit, "conclusion", "strongPoints", "weakPoints");

		System.out.println(super.getRequest().getData("courseId", int.class));
		Course course = repo.findCourse(super.getRequest().getData("courseId", int.class));
		System.out.println(course);
        audit.setCourse(course);

	}

	@Override
	public void validate(final Audit audit) {
		System.out.println("validate");
		assert audit != null;

		if (!super.getBuffer().getErrors().hasErrors("course")) {
			super.state(audit.getCourse() != null, "courseId", "auditor.audit.form.course.nullError");
		}
    }

	@Override
	public void perform(final Audit audit) {
		assert audit != null;
		repo.save(audit);
	}

	@Override
	public void unbind(final Audit audit) {
		System.out.println("unbind");
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