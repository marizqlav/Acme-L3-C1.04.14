package acme.testing.auditor.audit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditCreateTest extends TestHarness {
    
    @ParameterizedTest
    @CsvFileSource(resources = "auditor/audit/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    public void test100Positive(final int recordIndex, String conclusion, String strongPoints, String weakPoints, String course) {

        super.signIn("auditor1", "auditor1");

        super.clickOnMenu("Auditor", "My audits");
        super.checkListingExists();

        super.clickOnButton("Create");
        super.fillInputBoxIn("Conclusion", conclusion);
        super.fillInputBoxIn("Strong points", strongPoints);
        super.fillInputBoxIn("Weak points", weakPoints);
        super.fillInputBoxIn("Course:", course);
        super.clickOnSubmit("Create");

        super.clickOnMenu("Auditor", "My audits");
        super.checkListingExists();
        super.sortListing(0, "asc");
        super.checkColumnHasValue(recordIndex, 0, conclusion);
        super.checkColumnHasValue(recordIndex, 1, strongPoints);
        super.checkColumnHasValue(recordIndex, 2, weakPoints);

        super.clickOnListingRecord(recordIndex);
        super.checkFormExists();
        super.checkInputBoxHasValue("Conclusion", conclusion);
        super.checkInputBoxHasValue("Strong points", strongPoints);
        super.checkInputBoxHasValue("Weak points", weakPoints);

        super.clickOnButton("Show auditing records");
        super.checkListingExists();
        super.checkListingEmpty();

        super.signOut();
    }

}
