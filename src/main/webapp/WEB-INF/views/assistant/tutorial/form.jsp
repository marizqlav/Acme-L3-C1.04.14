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
	<h1><acme:message code="assistant.tutorial.data"/></h1>
	<acme:input-textarea code="assistant.tutorial.form.label.title" path="title"/>
	<acme:input-double code="assistant.tutorial.form.label.estimatedTime" path="estimatedTime"/>
	<acme:input-textarea code="assistant.tutorial.form.label.description" path="description"/>
	<acme:input-select code="assistant.tutorial.form.label.course" path="course" choices="${courses}"/>

	<br>
	<br>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorial.form.button.create" action="/assistant/tutorial/create?tutorialId=${tutorialId}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<jstl:if test="${draftMode==true}">
				<acme:submit code="assistant.tutorial.form.button.update" action="/assistant/tutorial/update"/>
				<acme:submit code="assistant.tutorial.form.button.delete" action="/assistant/tutorial/delete"/>
				<acme:submit code="assistant.tutorial.form.button.publish" action="/assistant/tutorial/publish"/>
			</jstl:if>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show') && draftMode == false}">
			<jstl:if test="${draftMode==false}">
				<acme:button code="assistant.tutorial.form.button.sessions" action="/assistant/session-tutorial/list?tutorialId=${tutorialId}"/>	
			</jstl:if>
		</jstl:when>			
	</jstl:choose>

</acme:form>