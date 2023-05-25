/*
 * EmployerJobShowTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.auditor.auditingRecord;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordShowTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
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

		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assesment", assesment);
		super.checkInputBoxHasValue("assesmentStartDate", assesmentStartDate);
		super.checkInputBoxHasValue("assesmentEndDate", assesmentEndDate);
		super.checkInputBoxHasValue("mark", mark);

		super.signOut();
	}

	@Test
	@Order(20)
	public void test300Hacking() {

		final List<AuditingRecord> auditingRecords = this.repo.findAllAuditingRecords();
		final AuditingRecord auditingRecord = auditingRecords.get(0);

		super.checkLinkExists("Sign in");
		super.request("/auditor/auditing-record/show", "id=" + auditingRecord.getId());
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/auditor/auditing-record/show", "id=" + auditingRecord.getId());
		super.checkPanicExists();
		super.signOut();
	}

}
