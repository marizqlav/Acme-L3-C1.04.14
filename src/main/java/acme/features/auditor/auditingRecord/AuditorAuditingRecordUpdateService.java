package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordUpdateService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository repo;

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		AuditingRecord auditingRecord = repo.findAuditingRecord(super.getRequest().getData("id", int.class));
		status = auditingRecord != null;

		Audit audit = auditingRecord.getAudit();
		if (audit != null) {
			status = audit.getDraftMode();
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		
		Integer id = super.getRequest().getData("id", int.class);
		AuditingRecord auditingRecord = repo.findAuditingRecord(id);

		super.getBuffer().setData(auditingRecord);
	}

	@Override
	public void bind(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		super.bind(auditingRecord, "subject", "assesment", "firstDate", "lastDate", "mark");
	}

	@Override
	public void validate(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

	}

	@Override
	public void perform(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		repo.save(auditingRecord);
	}

	@Override
	public void unbind(final AuditingRecord auditingRecord) {
        assert auditingRecord != null;

		Tuple tuple;

		tuple = super.unbind(auditingRecord, "subject", "assesment", "firstDate", "lastDate", "mark");

		tuple.put("draftMode", auditingRecord.getAudit().getDraftMode());

		super.getResponse().setData(tuple);
	}

}
