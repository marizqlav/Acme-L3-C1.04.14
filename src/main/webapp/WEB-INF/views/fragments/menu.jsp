<%--
- menu.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.roles.Provider,acme.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link" action="http://www.example.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.ismael-favourite-link" action="https://elchapuzasinformatico.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.ale-favourite-link" action="https://www.youtube.com/watch?v=xvFZjo5PgG0"/>
			<acme:menu-suboption code="master.menu.anonymous.alberto-favourite-link" action="https://forocoches.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.mario-favourite-link" action="http://letrinaheridos.blogspot.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.marcos-favourite-link" action="https://www.youtube.com/channel/UCi8z8yRzPpHWPs-TfUEkvEQ?app=desktop"/>
		</acme:menu-option>
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.offer.list" action="/authenticated/offer/list"/>
			<acme:menu-suboption code="master.menu.authenticated.note.list" action="/authenticated/note/list"/>
			<acme:menu-suboption code="master.menu.authenticated.course.list" action="/authenticated/course/list"/>
			<acme:menu-suboption code="master.menu.authenticated.bulletin.list" action="/authenticated/bulletin/list"/>
			<acme:menu-suboption code="master.menu.list.audits-by-course" action="/authenticated/audit/list-by-course"/>
		</acme:menu-option>
    <acme:menu-option code="master.menu.any.peeps" action="/any/peep/list"/>
    <acme:menu-option code="master.menu.any.course" action="/any/course/list" />

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
			<acme:menu-suboption code="master.menu.administrator.system-configuration" action="/administrator/system-configuration/show" access="isAuthenticated()"/>
			<acme:menu-suboption code="master.menu.administrator.list-offers" action="/administrator/offer/list" access="isAuthenticated()"/>
			<acme:menu-suboption code="master.menu.administrator.bulletin.create" action="/administrator/bulletin/create"/>
			
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.student" access="hasRole('Student')">
			<acme:menu-suboption code="master.menu.student.list.courses" action="/student/course/list"/>
		<acme:menu-separator/>	
		<acme:menu-suboption code="master.menu.student.my.enrolments" action="/student/enrolment/list-mine"/>
		<acme:menu-separator/>
		<acme:menu-suboption code="master.menu.student.dashboard" action="/student/student-dashboard/show"/>
		</acme:menu-option>
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.list.audits" action="/auditor/audit/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.company" access="hasRole('Company')">
			<acme:menu-suboption code="master.menu.company.favourite-link" action="https://www.informatica.us.es/"/>
			<acme:menu-separator/>	
			<acme:menu-suboption code="master.menu.company.practicum" action="/company/practicum/list"/>
			<acme:menu-suboption code="master.menu.company.companyDashboard" action="/company/company-dashboard/show"/>
		</acme:menu-option>

		
		
		<acme:menu-option code="master.menu.lecturer" access="hasRole('Lecturer')">
			<acme:menu-suboption code="master.menu.lecturer.list.courses" action="/lecturer/course/list"/>
			<acme:menu-suboption code="master.menu.lecturer.list.lecture" action="/lecturer/lecture/list"/>
			<acme:menu-suboption code="master.menu.lecturer.form.lecturer-dashboard" action="/lecturer/lecturer-dashboard/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.assistant" access="hasRole('Assistant')">
			<acme:menu-suboption code="master.menu.assistant.list.tutorial" action="/assistant/tutorial/list"/>
		</acme:menu-option>

	</acme:menu-left>
	

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-student" action="/authenticated/student/create" access="!hasRole('Student')"/>
			<acme:menu-suboption code="master.menu.user-account.student" action="/authenticated/student/update" access="hasRole('Student')"/>
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>

			<acme:menu-separator/>	

			<acme:menu-suboption code="master.menu.user-account.become-assistant" action="/authenticated/assistant/create" access="!hasRole('Assistant')"/>
			<acme:menu-suboption code="master.menu.user-account.assistant" action="/authenticated/assistant/update" access="hasRole('Assistant')"/>
			<acme:menu-suboption code="master.menu.user-account.become-company" action="/authenticated/company/create" access="!hasRole('Company')"/>
			<acme:menu-suboption code="master.menu.user-account.company" action="/authenticated/company/update" access="hasRole('Company')"/>
			<acme:menu-suboption code="master.menu.user-account.become-lecturer" action="/authenticated/lecturer/create" access="!hasRole('Lecturer')"/>
			<acme:menu-suboption code="master.menu.user-account.lecturer" action="/authenticated/lecturer/update" access="hasRole('Lecturer')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

