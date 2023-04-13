package acme.features.auditor.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditDeleteService extends AbstractService<Auditor, Audit> {

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
		Audit audit;

        audit = repo.findAudit(super.getRequest().getData("id", int.class));


		super.getBuffer().setData(audit);
	}

	@Override
	public void bind(final Audit audit) {
		assert audit != null;

		super.bind(audit, "code", "conclusion", "strongPoints", "weakPoints");
	}

	@Override
	public void validate(final Audit audit) {
		assert audit != null;
	}

	@Override
	public void perform(final Audit audit) {
		assert audit != null;

		for (final AuditingRecord record : repo.findRecordsFromAudit(audit.getId())) {
			repo.delete(record);
        }

		repo.delete(audit);
	}

	@Override
	public void unbind(final Audit audit) {
        assert audit != null;

		Tuple tuple;

		tuple = super.unbind(audit, "code", "conclusion", "strongPoints", "weakPoints");

		tuple.put("draftMode", audit.getDraftMode());

		super.getResponse().setData(tuple);
	}

}
