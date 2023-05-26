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

	
	<jstl:choose>
	<jstl:when test="${acme:anyOf(_command, 'show')}">
		<acme:input-textbox code="lecturer.course.form.label.code"  readonly="true" path="code"/>
		<acme:input-textbox code="lecturer.course.form.label.courseType"  readonly="true" path="courseType"/>
	</jstl:when>
	</jstl:choose>
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.course.form.label.resumen" path="resumen"/>
	<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice"/>
	<jstl:choose>
	<jstl:when test="${acme:anyOf(_command, 'show')}">
		<acme:input-money code="student.course.form.label.retail.price.exchange.money" readonly="true" path="exchangeMoney"/>
	</jstl:when>
	</jstl:choose>
	
	<acme:input-url code="lecturer.course.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show')}">
			<acme:button code="lecturer.course.button.lectures" action="/lecturer/lecture/listFromCourse?id=${id}"/>
			<jstl:if test="${draftmode==true}">
				<acme:button code="lecturer.course.button.courseLecture.create" action="/lecturer/course-lecture/create?id=${id}"/>
				<acme:button code="lecturer.course.button.courseLecture.list" action="/lecturer/course-lecture/list?courseId=${id}"/>
			
				<acme:submit code="lecturer.course.button.update" action="/lecturer/course/update?id=${id}"/>	
				<acme:submit code="lecturer.course.button.delete" action="/lecturer/course/delete"/>
				<jstl:if test="${hasLectures==true}">
					<acme:submit code="lecturer.course.button.publish" action="/lecturer/course/publish"/>
				</jstl:if>
				
			</jstl:if>
		</jstl:when>
	
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'update'}">
			<acme:submit code="lecturer.course.button.update" action="/lecturer/course/update"/>	
		</jstl:when>
		
	</jstl:choose>
	

</acme:form>
