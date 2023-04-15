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
<h1><acme:message code="authenticated.course.data"/></h1>
	<acme:input-textbox code="authenticated.course.form.label.title" readonly="true" path="title"/>	
	<acme:input-textarea code="authenticated.course.form.label.resumen" readonly="true" path="resumen"/>
	<acme:input-money code="authenticated.course.form.retail.price" readonly="true" path="retailPrice"/>
	<jstl:if test="${!link.isEmpty()}">
		<acme:input-url code="authenticated.course.form.label.link" readonly="true" path="link"/>
	</jstl:if>
	<acme:submit test="${_command == 'show'}" code="authenticated.practicum.form.button.list" action="/authenticated/practicum/list"/>
	
</acme:form>