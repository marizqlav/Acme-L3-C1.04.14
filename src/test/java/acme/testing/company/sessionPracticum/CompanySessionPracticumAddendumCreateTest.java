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
	@CsvFileSource(resources = "/company/session-practicum/create-positive-addendum.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link, final String confirmation) {

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
		super.fillInputBoxIn("confirmation", confirmation);
		super.clickOnSubmit("Create Addendum");

		super.checkListingExists();
		super.sortListing(0, "desc");

		super.clickOnListingRecord(sesionRecordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractSessionPracticum", abstractSessionPracticum);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/create-negative-addendum.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex) {

		super.signIn("company1", "company1");
		super.clickOnMenu("Company", "Practicum list");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("List Session Practicum");
		super.checkNotSubmitExists("Create Addendum");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/session-practicum/create-negative-addendum2.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test000Negative2(final int recordIndex, final int sesionRecordIndex, final String title, final String abstractSessionPracticum, final String startDate, final String finishDate, final String link, final String confirmation) {

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
		super.fillInputBoxIn("confirmation", confirmation);
		super.clickOnSubmit("Create Addendum");

		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {

		final Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practicums) {
			param = String.format("masterId=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/session-practicum/create-addendum", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/company/session-practicum/create-addendum", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {

		Collection<Practicum> practicums;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practicums)
			if (!practicum.isDraftMode()) {
				param = String.format("masterId=%d", practicum.getId());
				super.request("/company/session-practicum/create-addendum", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {

		Collection<Practicum> practicums;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicums = this.repository.findManyPracticumsByCompanyUsername("company2");
		for (final Practicum practicum : practicums) {
			param = String.format("masterId=%d", practicum.getId());
			super.request("/company/session-practicum/create-addendum", param);
			super.checkPanicExists();
		}
	}
}
