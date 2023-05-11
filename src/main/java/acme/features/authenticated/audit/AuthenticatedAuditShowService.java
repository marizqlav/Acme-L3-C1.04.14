package acme.features.authenticated.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuthenticatedAuditShowService extends AbstractService<Authenticated, Audit> {


	@Autowired
	protected AuthenticatedAuditRepository repo;

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

		final Auditor auditor = repo.findAuditorByAuditId(audit.getId());

		tuple = super.unbind(audit, "code", "conclusion", "strongPoints", "weakPoints");

		tuple.put("draftMode", audit.getDraftMode());
        
		tuple.put("userName", auditor.getUserAccount().getUsername());
		tuple.put("firm", auditor.getFirm());
		tuple.put("professionalID", auditor.getProfessionalID());
		tuple.put("listOfCertifications", auditor.getListOfCertifications());
		tuple.put("link", auditor.getLink());

		super.getResponse().setData(tuple);
	}


}
