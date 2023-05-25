
package acme.testing.auditor.auditingRecord;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditingRecordCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(50)
	public void test100Positive(final int recordIndex, final String subject, final String assesment, final String assesmentStartDate, final String assesmentEndDate, final String mark) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Show auditing records");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assesment", assesment);
		super.fillInputBoxIn("assesmentStartDate", assesmentStartDate);
		super.fillInputBoxIn("assesmentEndDate", assesmentEndDate);
		super.fillInputBoxIn("mark", mark);
		super.clickOnSubmit("Create");

		super.clickOnListingRecord(2);
		super.checkFormExists();

		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assesment", assesment);
		super.checkInputBoxHasValue("assesmentStartDate", assesmentStartDate);
		super.checkInputBoxHasValue("assesmentEndDate", assesmentEndDate);
		super.checkInputBoxHasValue("mark", mark);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(51)
	public void test200Negative(final int recordIndex, final String subject, final String assesment, final String assesmentStartDate, final String assesmentEndDate, final String mark) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Show auditing records");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assesment", assesment);
		super.fillInputBoxIn("assesmentStartDate", assesmentStartDate);
		super.fillInputBoxIn("assesmentEndDate", assesmentEndDate);
		if (mark != "" && mark != null)
			super.fillInputBoxIn("mark", mark);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	@Order(50)
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/auditor/auditing-record/create");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/auditor/auditing-record/create");
		super.checkPanicExists();
		super.signOut();
	}

}
