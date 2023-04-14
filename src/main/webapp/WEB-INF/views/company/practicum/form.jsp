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
	<acme:input-select code="company.practicum.form.label.course" path="course" choices="${courses}"/>
	<acme:input-textbox code="company.practicum.form.label.practicum-code" path="code"/>
	<acme:input-textbox code="company.practicum.form.label.practicum-title" path="title"/>
	<acme:input-textarea code="company.practicum.form.label.abstractPracticum" path="abstractPracticum"/>
	<acme:input-textarea code="company.practicum.form.label.someGoals" path="someGoals"/>
    
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="company.practicum.form.button.update" action="/company/practicum/update"/>
			<acme:submit code="company.practicum.form.button.delete" action="/company/practicum/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practicum.form.button.create" action="/company/practicum/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
