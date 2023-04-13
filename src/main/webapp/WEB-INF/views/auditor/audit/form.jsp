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

	<h1><acme:message code="auditor.audit.data"/></h1>
	<acme:input-textbox code="auditor.audit.form.label.code" path="code"/>	
	<acme:input-textarea code="auditor.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textarea code="auditor.audit.form.label.strongPoints" path="strongPoints"/>
	<acme:input-textarea code="auditor.audit.form.label.weakPoints" path="weakPoints"/>
	
	<jstl:if test="${_command == 'create'}">
		<acme:input-double code="auditor.audit.form.chooseCourse" path="courseId"/>
	</jstl:if>

	<jstl:if test="${_command == 'show'}">
		<acme:link code="auditor.audit.auditingRecords" action="/auditor/auditing-record/list?auditId=${id}"/>
	</jstl:if>

	<br>
	<br>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit.form.button.create" action="/auditor/audit/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<jstl:if test="${draftMode == true}">
				<acme:submit code="auditor.audit.form.button.update" action="/auditor/audit/update"/>
				<acme:submit code="auditor.audit.form.button.delete" action="/auditor/audit/delete"/>
			</jstl:if>		
		</jstl:when>	
	</jstl:choose>

</acme:form>
