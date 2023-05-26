
package acme.testing.auditor.audit;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditPublishTest extends TestHarness {

	@Autowired
	protected AuditorAuditTestRepository repo;


	@Test
	@Order(30)
	public void test100Positive() {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);

		super.checkFormExists();
		super.clickOnSubmit("Publish audit");
		super.checkNotErrorsExist();

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);

		super.checkFormExists();
		super.checkNotSubmitExists("Publish audit");

		super.signOut();
	}

	@Test
	@Order(30)
	public void test200Negative() {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(2);

		super.checkFormExists();
		super.checkNotSubmitExists("Publish audit");

		super.signOut();
	}

	@Test
	@Order(30)
	public void test300Hacking() {

		final List<Audit> audits = this.repo.findAllAudits();

		for (final Audit audit : audits)
			if (audit.getDraftMode()) {

				super.checkLinkExists("Sign in");
				super.request("/auditor/audit/publish", "id=" + audit.getId());
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/auditor/audit/publish", "id=" + audit.getId());
				super.checkPanicExists();
				super.signOut();

				return;
			}
	}

	@Test
	@Order(33)
	public void test301Hacking() {

		final List<Audit> audits = this.repo.findAllAudits();

		for (final Audit audit : audits)
			if (!audit.getDraftMode()) {

				super.signIn("auditor1", "auditor1");
				super.request("/auditor/audit/publish", "id=" + audit.getId());
				super.checkPanicExists();
				
				super.signOut();

				return;
			}
	}

}
