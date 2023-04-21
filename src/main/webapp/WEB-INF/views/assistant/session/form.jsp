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
	<h1><acme:message code="assistant.session.data"/></h1>
	<acme:input-textarea code="assistant.session.form.label.title" path="title"/>
	<acme:input-textarea code="assistant.session.form.label.description" path="description"/>
	<acme:input-textbox code="assistant.session.form.label.sessionType" path="sessionType"/>	
	<acme:input-textarea code="assistant.session.form.label.startDate" path="startDate"/>		
	<acme:input-textarea code="assistant.session.form.label.endDate" path="endDate"/>		
	<acme:input-textarea code="assistant.session.form.label.link" path="link"/>

	<br>
	<br>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.session.form.button.create" action="/assistant/session/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<jstl:if test="${draftMode == true}">
				<acme:submit code="assistant.session.form.button.update" action="/assistant/session/update"/>
				<acme:submit code="assistant.session.form.button.delete" action="/assistant/session/delete"/>
			</jstl:if>		
		</jstl:when>	
	</jstl:choose>

</acme:form>