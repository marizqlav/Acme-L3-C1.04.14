<%--
- list.jsp
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
<%@taglib prefix="custom" tagdir="/WEB-INF/tags"%>


<custom:form-get>
	<acme:input-select code="authenticated.audit.list.label.course" path="course" choices="${courses}"/>
	<acme:submit method="get" code="authenticated.audit.list.button.search-by-course" action="/authenticated/audit/list-by-course"/>
</custom:form-get>

<br>
<br>
<br>
<br>

<acme:list>
	<acme:list-column code="authenticated.audit.list.label.published" path="draftMode"/>
	<acme:list-column code="authenticated.audit.list.label.code" path="code"/>
	<acme:list-column code="authenticated.audit.list.label.conclusion" path="conclusion"/>
	<acme:list-column code="authenticated.audit.list.label.strongPoints" path="strongPoints"/>
	<acme:list-column code="authenticated.audit.list.label.weakPoints" path="weakPoints"/>
</acme:list>

