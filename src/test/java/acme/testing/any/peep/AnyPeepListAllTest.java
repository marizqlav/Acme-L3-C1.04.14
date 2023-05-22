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

package acme.testing.any.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepListAllTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String user, final String pass, final String title, final String nick) {
		// HINT: this test signs in as an employer, lists all of the jobs, 
		// HINT+ and then checks that the listing shows the expected data.

		if (user != null && pass != null)
			super.signIn(user, pass);

		super.clickOnMenu("Peeps");
		super.checkListingExists();

		if (nick != null)
			super.checkColumnHasValue(recordIndex, 1, nick);
		else
			super.checkColumnHasValue(recordIndex, 1, user);
		super.checkColumnHasValue(recordIndex, 2, title);

		if (user != null && pass != null)
			super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
	}

}
