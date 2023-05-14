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


<acme:form>
	<h1><acme:message code="company.practicum.data"/></h1>
	<jstl:if test="${(_command == 'create')}">
		<acme:input-select code="company.practicum.form.label.course" path="course" choices="${courses}"/>
	</jstl:if>
	<acme:input-textbox code="company.practicum.form.label.title" path="title"/>
	<acme:input-textarea code="company.practicum.form.label.abstractPracticum" path="abstractPracticum"/>
	<acme:input-textarea code="company.practicum.form.label.someGoals" path="someGoals"/>
	
	<jstl:if test="${!(_command == 'create')}">
		<acme:input-textbox code="company.practicum.form.label.code"  readonly="true" path="code"/>
		<acme:input-select code="company.practicum.form.label.course" path="course" choices="${courses}" readonly = "true"/>
		<jstl:if test="${estimatedTimeMenos != null}">
			<acme:input-double code="company.practicum.form.label.estimatedTimeMenos" path="estimatedTimeMenos" readonly = "true"/>
		</jstl:if>
		<jstl:if test="${estimatedTimeMas!= null}">
			<acme:input-double code="company.practicum.form.label.estimatedTimeMas" path="estimatedTimeMas" readonly = "true"/>
		</jstl:if>
		<jstl:if test="${estimatedTimeMas!= null}">
			<acme:input-double code="company.practicum.form.label.fechaInicial" path="fechaInicial" readonly = "true"/>
		</jstl:if>
		<jstl:if test="${fechaFinal!= null}">
			<acme:input-double code="company.practicum.form.label.fechaFinal" path="fechaFinal" readonly = "true"/>
		</jstl:if>	
		<h1><acme:message code="company.practicum.company.data"/></h1>
		<acme:input-textbox code="company.practicum.form.label.company.username" readonly="true" path="companyusername"/>
		<acme:input-textarea code="company.practicum.form.label.company.name" readonly="true" path="companyname"/>
		<acme:input-textarea code="company.practicum.form.label.company.VATNumber" readonly="true" path="companyVATNumber"/>
		<acme:input-textarea code="company.practicum.form.label.company.summary" readonly="true" path="companysummary"/>
		
	</jstl:if>
		
			<jstl:if test="${companylink != null}">
		<acme:input-url code="company.practicum.form.label.company.link" readonly="true" path="companylink"/>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="company.practicum.form.button.update" action="/company/practicum/update"/>
			<acme:submit code="company.practicum.form.button.delete" action="/company/practicum/delete"/>
			<acme:submit code="company.practicum.form.button.publish" action="/company/practicum/publish"/>
			<acme:button code="company.practicum.form.button.list" action="/company/session-practicum/list?practicumId=${id}"/>
		</jstl:when>		
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practicum.form.button.create" action="/company/practicum/create"/>
		</jstl:when>
		<jstl:when test="${_command == 'show'}">
			<acme:button code="company.practicum.form.button.list" action="/company/session-practicum/list?practicumId=${id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
