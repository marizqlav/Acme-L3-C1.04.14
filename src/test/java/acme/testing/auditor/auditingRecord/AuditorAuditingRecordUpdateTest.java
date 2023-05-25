
package acme.testing.auditor.auditingRecord;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordUpdateTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(30)
	public void test100Positive(final int recordIndex, final int auditingRecordRecordIndex, final String subject, final String assesment, final String assesmentStartDate, final String assesmentEndDate, final String mark) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Show auditing records");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assesment", assesment);
		super.fillInputBoxIn("assesmentStartDate", assesmentStartDate);
		super.fillInputBoxIn("assesmentEndDate", assesmentEndDate);
		super.fillInputBoxIn("mark", mark);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assesment", assesment);
		super.checkInputBoxHasValue("assesmentStartDate", assesmentStartDate);
		super.checkInputBoxHasValue("assesmentEndDate", assesmentEndDate);
		super.checkInputBoxHasValue("mark", mark);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(31)
	public void test200Negative(final int recordIndex, final int auditingRecordRecordIndex, final String subject, final String assesment, final String assesmentStartDate, final String assesmentEndDate, final String mark) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Show auditing records");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assesment", assesment);
		super.fillInputBoxIn("assesmentStartDate", assesmentStartDate);
		super.fillInputBoxIn("assesmentEndDate", assesmentEndDate);
		if (mark != "" && mark != null) {
			super.fillInputBoxIn("mark", mark);
		}
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	@Order(30)
	public void test300Hacking() {

		final List<AuditingRecord> auditingRecords = this.repo.findAllAuditingRecords();
		final AuditingRecord auditingRecord = auditingRecords.get(0);

		super.checkLinkExists("Sign in");
		super.request("/auditor/audit/update", "id=" + auditingRecord.getId());
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/auditor/audit/update", "id=" + auditingRecord.getId());
		super.checkPanicExists();
		super.signOut();
	}

}
