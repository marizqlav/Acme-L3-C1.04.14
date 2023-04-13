package acme.features.auditor.audit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditListService extends AbstractService<Auditor, Audit> {

    @Autowired
    AuditorAuditRepository repo;

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
        List<Audit> audits = repo.findAllAuditsFromAuditor(super.getRequest().getPrincipal().getActiveRoleId());

        super.getBuffer().setData(audits);
    }

    @Override
    public void unbind(final Audit audit) {
		assert audit != null;

		Tuple tuple;

		tuple = super.unbind(audit, "code", "conclusion", "strongPoints", "weakPoints");

        super.getResponse().setData(tuple);
    }

}
