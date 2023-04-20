package acme.features.auditor.auditingRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audits.AuditingRecord;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditingRecordController extends AbstractController<Auditor, AuditingRecord> {
    
    @Autowired
    AuditorAuditingRecordListService listService;

    @Autowired
    AuditorAuditingRecordShowService showService;

    @Autowired
    AuditorAuditingRecordCreateService createService;

    @Autowired
    AuditorAuditingRecordUpdateService updateService;

    @Autowired
    AuditorAuditingRecordDeleteService deleteService;

    @Autowired
    AuditorAuditingRecordCorrectService correctService;

    @PostConstruct
    void initialise() {
        super.addBasicCommand("list", listService);
        super.addBasicCommand("show", showService);
        super.addBasicCommand("create", createService);
        super.addBasicCommand("update", updateService);
        super.addBasicCommand("delete", deleteService);

        super.addCustomCommand("correct", "create", correctService);
    }

}
