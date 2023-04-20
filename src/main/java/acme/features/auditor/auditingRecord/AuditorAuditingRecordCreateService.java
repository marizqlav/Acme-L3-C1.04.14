package acme.features.auditor.auditingRecord;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.audits.MarkType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordCreateService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("auditId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status = true;

		Audit audit = repo.findAudit(super.getRequest().getData("auditId", int.class));
		if (audit != null) {
			status = audit.getDraftMode();
		}

		//TODO Añadir lo de la confirmacion y tal

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord auditingRecord = new AuditingRecord();

		super.getBuffer().setData(auditingRecord);
	}

	@Override
	public void bind(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		super.bind(auditingRecord, "subject", "assesment", "firstDate", "lastDate", "mark");

		Audit audit = repo.findAudit(super.getRequest().getData("auditId", int.class));
        auditingRecord.setAudit(audit);
	}

	@Override
	public void validate(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		if (!super.getBuffer().getErrors().hasErrors("audit")) {
			super.state(auditingRecord.getAudit() != null, "audit", "auditor.auditingRecord.form.audit.nullError");
		}

		//TODO Check dates

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
		
		tuple = super.unbind(auditingRecord, "subject", "assesment", "firstDate", "lastDate", "mark", "draftMode");

		SelectChoices choices = SelectChoices.from(MarkType.class, auditingRecord.getMark());

        tuple.put("marks", choices);
        tuple.put("mark", auditingRecord.getMark());

		super.getResponse().setData(tuple);
	}
}