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

package acme.testing.any.peep;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepShowTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String user, final String pass, final String title, final String nick, final String message, final String moreInfo, final String link) {

		if (user != null && pass != null)
			super.signIn(user, pass);

		super.clickOnMenu("Peeps");
		super.checkListingExists();
		super.sortListing(1, "asc");

		if (nick != null)
			super.checkColumnHasValue(recordIndex, 1, nick);
		else
			super.checkColumnHasValue(recordIndex, 1, user);
		super.checkColumnHasValue(recordIndex, 2, title);
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		if (nick != null)
			super.checkInputBoxHasValue("nick", nick);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("message", message);
		if (moreInfo != null)
			super.checkInputBoxHasValue("moreInfo", moreInfo);
		if (link != null)
			super.checkInputBoxHasValue("link", link);

		if (user != null && pass != null)
			super.signOut();
	}

	//	@Test
	//	public void test200Negative() {
	//		// HINT: there aren't any negative tests for this feature because it's a listing
	//		// HINT+ that doesn't involve entering any data in any forms.
	//	}
}
