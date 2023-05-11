/*
 * EmployerJobListMineTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.company.sessionPracticum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class CompanySessionPracticumListAllTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum) {
		// HINT: this test signs in as an employer, lists all of the jobs, 
		// HINT+ and then checks that the listing shows the expected data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List Session Practicum");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(sesionRecordIndex, 0, title);
		super.checkColumnHasValue(sesionRecordIndex, 1, abstractSessionPracticum);

		super.signOut();

	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list all of the session-practicums using 
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/company/session-practicum/list");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/company/session-practicum/list");
		super.checkPanicExists();
		super.signOut();

	}

}
