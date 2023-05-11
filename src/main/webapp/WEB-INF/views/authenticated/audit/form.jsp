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

	<h6><acme:message code="auditor.audit.form.label.code"/></h6>
	<acme:print value="${code}"/>

	<br>
	<br>

	<h1><acme:message code="auditor.audit.auditor.data"/></h1>
	<acme:input-textbox code="auditor.audit.form.label.auditor.userName" path="userName"/>
	<acme:input-textarea code="auditor.audit.form.label.auditor.firm" path="firm"/>
	<acme:input-textarea code="auditor.audit.form.label.auditor.professionalID" path="professionalID"/>
	<acme:input-textarea code="auditor.audit.form.label.auditor.listOfCertifications" path="listOfCertifications"/>
	<acme:input-url code="auditor.audit.form.label.auditor.link" path="link"/>

</acme:form>
