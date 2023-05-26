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

package acme.testing.student.enrolment;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.springframework.core.annotation.Order;

import acme.framework.testing.BrowserDriver;
import acme.testing.TestHarness;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentEnrolmentCreateTest extends TestHarness {

	@Order(10)
	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String courseTitle, final String code, final String motivation, final String someGoals) {

		// This test authenticates as a student and then lists his or her
		// enrolments, creates a new one, and check that it's been created properly.

		super.signIn("student3", "student3");
		super.clickOnMenu("Student", "My enrolments");

		super.checkListingExists();
		super.clickOnButton("New enrolment");
		final BrowserDriver driver = super.getDriver();
		driver.locateOne(By.xpath("/html/body/div[2]/div/form/div[1]/select/option[" + (recordIndex + 2) + "]")).click();
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("someGoals", someGoals);
		super.clickOnSubmit("Create enrolment");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, courseTitle);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("workTime", "0.00");
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("someGoals", someGoals);

		super.signOut();
	}

	@Order(20)
	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/create-positive2.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest2(final int recordIndex, final int courseRecordIndex, final String courseTitle, final String code, final String motivation, final String someGoals) {

		//This test authenticates you as a student and then lists the available 
		//courses, creates an enrollment from the course you want, and verifies 
		//that it was created successfully.

		super.signIn("student2", "student2");
		super.clickOnMenu("Student", "Course list");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(courseRecordIndex, 0, courseTitle);
		super.clickOnListingRecord(courseRecordIndex);

		super.clickOnButton("New enrolment");
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("someGoals", someGoals);
		super.clickOnSubmit("Create enrolment");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, courseTitle);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("workTime", "0.00");
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("someGoals", someGoals);

		super.signOut();
	}

	@Order(30)
	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeTest(final int recordIndex, final String motivation, final String someGoals) {

		// This test attempts to create enrolments with incorrect data.

		super.signIn("student3", "student3");
		super.clickOnMenu("Student", "My enrolments");
		super.clickOnButton("New enrolment");
		super.checkFormExists();

		final BrowserDriver driver = super.getDriver();
		driver.locateOne(By.xpath("/html/body/div[2]/div/form/div[1]/select/option[" + (recordIndex + 1) + "]")).click();
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("someGoals", someGoals);
		super.clickOnSubmit("Create enrolment");

		super.checkErrorsExist();
		super.signOut();
	}

	@Order(40)
	@Test
	public void test300Hacking() {

		// This test tries to create an enrolment using principals with
		// inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/create");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/student/enrolment/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/student/enrolment/create");
		super.checkPanicExists();
		super.signOut();
	}

}
