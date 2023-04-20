package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordShowService extends AbstractService<Auditor, AuditingRecord> {


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
        super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditingRecord auditingRecord;
		int id;

		id = super.getRequest().getData("id", int.class);
		auditingRecord = repo.findAuditingRecord(id);

		super.getBuffer().setData(auditingRecord);
	}

	@Override
	public void unbind(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		Tuple tuple;

		tuple = super.unbind(auditingRecord, "subject", "assesment", "firstDate", "lastDate", "mark", "correction");

		tuple.put("draftMode", auditingRecord.getAudit().getDraftMode());

		super.getResponse().setData(tuple);
	}

}
