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

	<jstl:if test="${draftMode}">
		<h5><acme:message code="auditor.audit.form.label.not-publish"/></h5>
	</jstl:if>
	<jstl:if test="${!draftMode}">
		<h5><acme:message code="auditor.audit.form.label.publish"/></h5>
	</jstl:if>

	<acme:input-textarea code="auditor.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textarea code="auditor.audit.form.label.strongPoints" path="strongPoints"/>
	<acme:input-textarea code="auditor.audit.form.label.weakPoints" path="weakPoints"/>

	<jstl:if test="${_command == 'create'}">
		<acme:input-select code="company.practicum.form.label.course" path="course" choices="${courses}"/>
	</jstl:if>


	<jstl:if test="${_command != 'create'}">
		<acme:input-textbox code="auditor.audit.form.label.code" readonly="true" path="code"/>
		<acme:input-textbox code="auditor.audit.form.label.mark" readonly="true" path="mark"/>
		<acme:input-textbox code="auditor.audit.form.label.course" readonly="true" path="course"/>
		<br>
		<br>
	</jstl:if>

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

	<br>
	<br>

	<jstl:if test="${draftMode == true && emptyRecords != true}">
		<acme:submit code="auditors.auditingRecord.list.button.publish" action="/auditor/audit/publish"/>
	</jstl:if>

	<br>
	<br>
	
	<jstl:if test="${acme:anyOf(_command, 'show|update')}">
		<acme:button code="auditor.audit.auditingRecords" action="/auditor/auditing-record/list?auditId=${id}"/>
	</jstl:if>

	<br>
	<br>


</acme:form>
