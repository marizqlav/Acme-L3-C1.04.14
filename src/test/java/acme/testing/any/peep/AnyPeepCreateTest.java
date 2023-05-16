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

package acme.testing.any.peep;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String user, final String pass, final String title, final String nick, final String message, final String moreInfo, final String link) {

		if (user != null && pass != null)
			super.signIn(user, pass);

		super.clickOnMenu("Peeps");
		super.checkListingExists();

		super.clickOnButton("Create");
		if (nick != null)
			super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("message", message);
		if (moreInfo != null)
			super.fillInputBoxIn("moreInfo", moreInfo);
		if (link != null)
			super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Peeps");
		super.checkListingExists();
		super.sortListing(0, "asc");
		if (nick != null)
			super.checkColumnHasValue(recordIndex, 1, nick);
		else
			super.checkColumnHasValue(recordIndex, 1, user);
		super.checkColumnHasValue(recordIndex, 2, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		if (nick != null)
			super.checkInputBoxHasValue("nick", nick);
		else
			super.checkInputBoxHasValue("nick", user);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("message", message);
		if (moreInfo != null)
			super.checkInputBoxHasValue("moreInfo", moreInfo);
		if (link != null)
			super.checkInputBoxHasValue("link", link);

		if (user != null && pass != null)
			super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeTest(final int recordIndex, final String user, final String pass, final String title, final String nick, final String message, final String moreInfo, final String link) {

		if (user != null && pass != null)
			super.signIn(user, pass);

		super.clickOnMenu("Peeps");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();
		if (nick != null)
			super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("message", message);
		if (moreInfo != null)
			super.fillInputBoxIn("moreInfo", moreInfo);
		if (link != null)
			super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();
		if (user != null && pass != null)
			super.signOut();

	}

}
