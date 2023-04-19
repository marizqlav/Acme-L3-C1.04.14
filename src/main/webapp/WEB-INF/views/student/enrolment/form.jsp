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
<jstl:if test="${!(_command == 'finalise')}">
	<jstl:if test="${!(_command == 'create')}">
	<acme:input-textbox code="student.enrolment.form.label.code" readonly="true" path="code"/>
	</jstl:if>
	<jstl:if test="${_command == 'create'}">
	<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courses}"/>
	</jstl:if>
	<acme:input-textarea code="student.enrolment.form.label.motivation" path="motivation"/>	
	<acme:input-textarea code="student.enrolment.form.label.someGoals" path="someGoals"/>	
<jstl:if test="${draftMode.equals(true)}">
	<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
	<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
	<hr/>
	<acme:button code="student.enrolment.finalise" action="/student/enrolment/finalise?id=${id}"/>
</jstl:if>
</jstl:if>
<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.enrolment.form.button.create" action="/student/enrolment/create"/>
		</jstl:when>	
	</jstl:choose>
	<jstl:choose>
		<jstl:when test="${_command == 'finalise'}">
		<form>
		<acme:input-textbox code="student.enrolment.form.label.holder.name" placeholder="student.enrolment.placeholder.holder.name" path="holderName" />
		<acme:input-integer code="student.enrolment.form.label.card.number" placeholder="student.enrolment.placeholder.card.number" path="lowerNibble"/>
		<!-- Expire Date no será un input-moment debido a que la fecha de las tarjetas de creditos tienen el formato MM/YYYY -->
		<acme:input-textbox code="student.enrolment.form.label.expire.date"  placeholder="student.enrolment.placeholder.expire.date" path="expireDate"/>
		<acme:input-integer code="student.enrolment.form.label.cvc" placeholder="student.enrolment.placeholder.cvc" path="cvc"/>
		<acme:submit code="student.enrolment.form.button.finalise"  action="/student/enrolment/finalise"/>
		</form>		
		</jstl:when>	
	</jstl:choose>
	<jstl:if test="${_command == 'show'}">
	<hr/>
		<acme:button code="student.course.activities" action="/student/activity/list?masterId=${id}"/>
	<hr/>
	</jstl:if>
</acme:form>


