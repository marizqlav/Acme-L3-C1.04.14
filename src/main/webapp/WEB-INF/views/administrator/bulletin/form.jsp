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
<h1><acme:message code="administrator.bulletin.data"/></h1>

	<acme:input-textbox code="administrator.bulletin.form.label.title"   path="title"/>	
	<acme:input-textarea code="administrator.bulletin.form.label.message"   path="message"/>
	<acme:input-checkbox code="administrator.bulletin.form.critical"   path="critical"/>
	<acme:input-moment code="authenticated.note.form.label.instantiationMoment" path="date" readonly="true"/>	
	<jstl:if test="${!link.isEmpty()}">
		<acme:input-url code="administrator.bulletin.form.label.link"   path="link"/>
	</jstl:if>
	
	<jstl:if test="${ _command == 'create'}">
		<acme:message code="administrator.bulletin.form.warning.confirmation" />
		<acme:input-checkbox code="administrator.bulletin.form.button.confirmation" path="confirmation"/>
		<acme:submit code="administrator.bulletin.form.button.create" action="/administrator/bulletin/create"/>
	</jstl:if>
</acme:form>