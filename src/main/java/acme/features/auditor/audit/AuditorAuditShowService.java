package acme.features.auditor.audit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.audits.MarkType;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {


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
        super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = repo.findAudit(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit audit) {
		assert audit != null;

		Tuple tuple;

		tuple = super.unbind(audit, "code", "conclusion", "strongPoints", "weakPoints", "id");

		tuple.put("draftMode", audit.getDraftMode());

		Map<MarkType, Integer> dic = new HashMap<>();
		for (AuditingRecord ar : repo.findRecordsFromAudit(audit.getId())) {
			if (!dic.containsKey(ar.getMark())) {
				dic.put(ar.getMark(), 0);
			}
			dic.replace(ar.getMark(), dic.get(ar.getMark()) + 1);
		}
		if (dic.entrySet().isEmpty()) {
			tuple.put("mark", "No mark");
		} else {
			MarkType mark = dic.entrySet().stream().max((x, y) -> x.getValue().compareTo(y.getValue())).get().getKey();
			tuple.put("mark", mark);
		}

		super.getResponse().setData(tuple);
	}


}
