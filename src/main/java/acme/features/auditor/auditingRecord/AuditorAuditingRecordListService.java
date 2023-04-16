
package acme.features.auditor.auditingRecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordListService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	AuditorAuditingRecordRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("auditId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final List<AuditingRecord> auditingRecords = this.repo.findAllAuditingRecordsFromAudit(super.getRequest().getData("auditId", int.class));

		super.getBuffer().setData(auditingRecords);
	}

	@Override
	public void unbind(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		Tuple tuple;

		tuple = super.unbind(auditingRecord, "subject", "assesment", "firstDate", "lastDate", "mark");

		tuple.put("draftMode", this.repo.findAudit(super.getRequest().getData("auditId", int.class)).getDraftMode());

		super.getResponse().setData("data", tuple);
	}

}
