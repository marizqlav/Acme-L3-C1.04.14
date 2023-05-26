
package acme.testing.auditor.audit;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditDeleteTest extends TestHarness {

	@Autowired
	protected AuditorAuditTestRepository repo;

	@Test
	@Order(50)
	public void test100Positive() {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();

		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();

		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.signOut();
	}

	@Test
	@Order(50)
	public void test300Hacking() {

		final List<Audit> audits = this.repo.findAllAudits();
		final Audit audit = audits.get(0);

		super.checkLinkExists("Sign in");
		super.request("/auditor/audit/delete", "id=" + audit.getId());
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/auditor/audit/delete", "id=" + audit.getId());
		super.checkPanicExists();
		super.signOut();

	}

}
