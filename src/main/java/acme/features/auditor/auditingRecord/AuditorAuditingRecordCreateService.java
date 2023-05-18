
package acme.features.auditor.auditingRecord;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

		final Audit audit = this.repo.findAudit(super.getRequest().getData("auditId", int.class));
		if (audit != null)
			status = audit.getDraftMode();
		else if (audit == null)
			status = false;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final AuditingRecord auditingRecord = new AuditingRecord();

		super.getBuffer().setData(auditingRecord);
	}

	@Override
	public void bind(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		super.bind(auditingRecord, "subject", "assesment", "assesmentStartDate", "assesmentEndDate", "mark");

		final Audit audit = this.repo.findAudit(super.getRequest().getData("auditId", int.class));
		auditingRecord.setAudit(audit);
	}

	@Override
	public void validate(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		if (!super.getBuffer().getErrors().hasErrors("assesmentEndDate"))
			if (auditingRecord.getAssesmentEndDate() != null)
				super.state(auditingRecord.getAssesmentEndDate().after(auditingRecord.getAssesmentStartDate()), "assesmentEndDate", "auditor.auditingRecord.form.assesmentEndDate.notPast");

		if (!super.getBuffer().getErrors().hasErrors("assesmentEndDate"))
			if (auditingRecord.getAssesmentEndDate() != null) {
				final LocalDateTime firstDate = auditingRecord.getAssesmentStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				final LocalDateTime lastDate = auditingRecord.getAssesmentEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

				super.state(Duration.between(firstDate, lastDate).toHours() >= 1, "assesmentEndDate", "auditor.auditingRecord.form.assesmentEndDate.notAnHour");
			}

	}

	@Override
	public void perform(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		this.repo.save(auditingRecord);
	}

	@Override
	public void unbind(final AuditingRecord auditingRecord) {
		assert auditingRecord != null;

		Tuple tuple;

		tuple = super.unbind(auditingRecord, "subject", "assesment", "assesmentStartDate", "assesmentEndDate");

		final boolean draftMode = this.repo.findAudit(super.getRequest().getData("auditId", int.class)).getDraftMode();

		tuple.put("correction", auditingRecord.isCorrection());
		tuple.put("draftMode", draftMode);

		final SelectChoices choices = SelectChoices.from(MarkType.class, auditingRecord.getMark());
		tuple.put("marks", choices);
		tuple.put("mark", auditingRecord.getMark());

		tuple.put("auditId", super.getRequest().getData("auditId", int.class));

		super.getResponse().setData(tuple);
	}
}
