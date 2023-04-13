package acme.features.authenticated.audit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audits.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedAuditController extends AbstractController<Authenticated, Audit> {
    

    @Autowired
    AuthenticatedAuditShowService showService;

    @Autowired
    AuthenticatedAuditListByCourseService listByCourseService;

    @PostConstruct
    void initialise() {
        super.addBasicCommand("show", showService);
        
        super.addCustomCommand("list-by-course", "list", listByCourseService);

    }

}
