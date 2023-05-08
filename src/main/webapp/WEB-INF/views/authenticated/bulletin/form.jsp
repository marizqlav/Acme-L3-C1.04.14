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
<h1><acme:message code="authenticated.bulletin.data"/></h1>
	<acme:input-textbox code="authenticated.bulletin.form.label.title" readonly="true" path="title"/>	
	<acme:input-textarea code="authenticated.bulletin.form.label.date" readonly="true" path="date"/>
	<acme:input-textarea code="authenticated.bulletin.form.label.message" readonly="true" path="message"/>
	<acme:input-textarea code="authenticated.bulletin.form.critical" readonly="true" path="critical"/>
	<jstl:if test="${!link.isEmpty()}">
		<acme:input-url code="authenticated.bulletin.form.label.link" readonly="true" path="link"/>
	</jstl:if>
</acme:form>