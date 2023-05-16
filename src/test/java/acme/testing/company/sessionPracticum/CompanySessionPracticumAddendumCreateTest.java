/*
 * EmployerJobCreateTest.java
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

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicum.Practicum;
import acme.testing.TestHarness;

public class CompanySessionPracticumAddendumCreateTest extends TestHarness {

	@Autowired
	protected CompanySessionPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	@Order(10)
	public void positiveTest(final int recordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link, final String practicumTitle) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List Session Practicum");

		super.clickOnButton("Create Addendum");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractSessionPracticum", abstractSessionPracticum);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abstractSessionPracticum);
		super.checkColumnHasValue(recordIndex, 2, practicumTitle);

		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractSessionPracticum", abstractSessionPracticum);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	@Order(20)
	public void negativeTest(final int recordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Session Practicum");

		super.clickOnButton("Create Addendum");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractSessionPracticum", abstractSessionPracticum);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();
		super.signOut();
	}

	//	@Order(30)
	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a job using principals with
		// HINT+ inappropriate roles.

		final Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("employer1");
		for (final Practicum practicum : practicums) {
			param = String.format("masterId=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/session-practicum/create", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/company/session-practicum/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to create a duty for a published job created by 
		// HINT+ the principal.

		Collection<Practicum> practicums;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practicums)
			if (!practicum.isDraftMode()) {
				param = String.format("masterId=%d", practicum.getId());
				super.request("/company/session-practicum/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to create duties for jobs that weren't created 
		// HINT+ by the principal.

		Collection<Practicum> practicums;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicums = this.repository.findManyPracticumsByCompanyUsername("company2");
		for (final Practicum practicum : practicums) {
			param = String.format("masterId=%d", practicum.getId());
			super.request("/company/session-practicum/create", param);
			super.checkPanicExists();
		}
	}

}
