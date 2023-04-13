package acme.features.auditor.audit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audits.Audit;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditController extends AbstractController<Auditor, Audit> {
    
    @Autowired
    AuditorAuditListService listService;

    @Autowired
    AuditorAuditShowService showService;

    @Autowired
    AuditorAuditCreateService createService;

    @Autowired
    AuditorAuditUpdateService updateService;

    @Autowired
    AuditorAuditDeleteService deleteService;

    @PostConstruct
    void initialise() {
        super.addBasicCommand("list", listService);
        super.addBasicCommand("show", showService);
        super.addBasicCommand("create", createService);
        super.addBasicCommand("update", updateService);
        super.addBasicCommand("delete", deleteService);
    }

}
