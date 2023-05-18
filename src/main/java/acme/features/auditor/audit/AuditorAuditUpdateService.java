package acme.features.auditor.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditUpdateService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repo;

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		Audit audit = repo.findAudit(super.getRequest().getData("id", int.class));
		status = audit != null;

		status = status && audit.getDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		
		Integer id = super.getRequest().getData("id", int.class);
		Audit audit = repo.findAudit(id);

		super.getBuffer().setData(audit);
	}

	@Override
	public void bind(final Audit audit) {
		assert audit != null;

		super.bind(audit, "conclusion", "strongPoints", "weakPoints");
	}

	@Override
	public void validate(final Audit audit) {
		assert audit != null;

		// if (!super.getBuffer().getErrors().hasErrors("code")) {
		// 	super.state(repo.findByCode(audit.getCode()) == null, "code", "auditor.audit.form.code.repeated");
		// }

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

		tuple.put("draftMode", audit.getDraftMode());

		if (repo.findRecordsFromAudit(audit.getId()).isEmpty()) {
			tuple.put("emptyRecords", true);
		} else {
			tuple.put("emptyRecords", false);
		}

		super.getResponse().setData(tuple);
	}

}
