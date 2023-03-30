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
<h1><acme:message code="student.course.data"/></h1>
	<acme:input-textbox code="student.course.form.label.title" path="title"/>	
	<acme:input-textarea code="student.course.form.label.resumen" path="resumen"/>
	<acme:input-money code="student.course.form.retail.price" path="retailPrice"/>
	<acme:input-url code="student.course.form.label.link" path="link"/>
	
<h1><acme:message code="student.course.lecturer.data"/></h1>
	<acme:input-textbox code="student.course.form.label.lecturer.username" path="lecturerusername"/>
	<acme:input-textarea code="student.course.form.label.lecturer.alma.mater" path="lectureralmamater"/>
	<acme:input-textarea code="student.course.form.label.lecturer.resume" path="lecturerresume"/>
	<acme:input-textarea code="student.course.form.label.lecturer.qualifications" path="lecturerqualifications"/>
	<acme:input-url code="student.course.form.label.lecturer.link" path="lecturerlink"/>
	</acme:form>
