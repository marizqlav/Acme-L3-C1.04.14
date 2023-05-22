<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly = "true">
	<acme:input-textbox code="authenticated.practicum.form.label.code" path="code"/>
	<acme:input-textbox code="authenticated.practicum.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.practicum.form.label.abstractPracticum" path="abstractPracticum"/>
	<acme:input-textbox code="authenticated.practicum.form.label.someGoals" path="someGoals"/>
		<jstl:if test="${companyusername != null && companyname != null && companyVATNumber != null && companysummary != null}">
			<h1><acme:message code="company.practicum.company.data"/></h1>
		</jstl:if>
		<jstl:if test="${companyusername != null }">
			<acme:input-textbox code="company.practicum.form.label.company.username" readonly="true" path="companyusername"/>
		</jstl:if>
		<jstl:if test="${companyname != null }">
			<acme:input-textarea code="company.practicum.form.label.company.name" readonly="true" path="companyname"/>
		</jstl:if>
		<jstl:if test="${ companyVATNumber != null }">
			<acme:input-textarea code="company.practicum.form.label.company.VATNumber" readonly="true" path="companyVATNumber"/>
		</jstl:if>
		<jstl:if test="${ companysummary != null }">
			<acme:input-textarea code="company.practicum.form.label.company.summary" readonly="true" path="companysummary"/>
		</jstl:if>
</acme:form>