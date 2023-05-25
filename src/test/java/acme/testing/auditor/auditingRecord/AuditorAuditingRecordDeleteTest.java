
package acme.testing.auditor.auditingRecord;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordDeleteTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repo;


	@Test
	@Order(40)
	public void test100Positive() {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();

		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.clickOnButton("Show auditing records");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();

		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.signOut();
	}

	@Test
	@Order(40)
	public void test300Hacking() {

		final List<AuditingRecord> auditingRecords = this.repo.findAllAuditingRecords();
		final AuditingRecord auditingRecord = auditingRecords.get(0);

		super.checkLinkExists("Sign in");
		super.request("/auditor/auditing-record/delete", "id=" + auditingRecord.getId());
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/auditor/auditing-record/delete", "id=" + auditingRecord.getId());
		super.checkPanicExists();
		super.signOut();

	}

}
