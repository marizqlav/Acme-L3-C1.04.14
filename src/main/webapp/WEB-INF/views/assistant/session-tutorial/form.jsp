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
	<h1><acme:message code="assistant.sessionTutorial.data"/></h1>
	<acme:input-textarea code="assistant.sessionTutorial.form.label.title" path="title"/>
	<acme:input-textarea code="assistant.sessionTutorial.form.label.description" path="description"/>
	<acme:input-select code="assistant.sessionTutorial.form.label.sessionType" path="sessionType" choices="${sessionTypes}"/>
	<acme:input-textarea code="assistant.sessionTutorial.form.label.startDate" path="startDate"/>
	<acme:input-textarea code="assistant.sessionTutorial.form.label.endDate" path="endDate"/>
	<acme:input-textarea code="assistant.sessionTutorial.form.label.link" path="link"/>

	<br>
	<br>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorial.list.button.create" action="/assistant/session-tutorial/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="assistant.tutorial.form.button.update" action="/assistant/session-tutorial/update"/>
			<acme:submit code="assistant.tutorial.form.button.delete" action="/assistant/session-tutorial/delete"/>
			<acme:submit code="assistant.tutorial.form.button.publish" action="/assistant/session-tutorial/publish"/>
		</jstl:when>	
	</jstl:choose>

</acme:form>