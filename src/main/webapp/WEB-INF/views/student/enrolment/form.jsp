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
	<acme:input-textbox code="employer.job.form.label.reference" path="reference"/>
	<acme:input-select code="employer.job.form.label.contractor" path="contractor" choices="${contractors}"/>	
	<acme:input-textbox code="employer.job.form.label.title" path="title"/>
	<acme:input-moment code="employer.job.form.label.deadline" path="deadline"/>
	<acme:input-money code="employer.job.form.label.salary" path="salary"/>
	<acme:input-double code="employer.job.form.label.score" path="score" placeholder="employer.job.form.placeholder.score"/>
	<acme:input-url code="employer.job.form.label.moreInfo" path="moreInfo"/>
	<acme:input-textarea code="employer.job.form.label.description" path="description"/>

</acme:form>
