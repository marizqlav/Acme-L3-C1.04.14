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

<h1><acme:message code="student.dashboard.title"/></h1>
<table class="table table-sm">
	<tr>
	<th><h2><acme:message code="student.dashboard.form.total.numer.activities"/></h2></th>
	</tr>
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.label.total.number.theory.activities"/></th>
		<td><acme:print value="${totalNumberTheoryActivities}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.label.total.number.hands.on.activities"/></th>
		<td><acme:print value="${totalNumberHandsOnActivities}"/></td>
	</tr>
	<tr>
	<th><h2><acme:message code="student.dashboard.form.period.activities"/></h2></th>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.period.activities.avg"/></th>
		<td><acme:print value="${statisticsPeriodActivitiesWorkbook.Avg}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.period.activities.deviation"/></th>
		<td><acme:print value="${statisticsPeriodActivitiesWorkbook.Deviation}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.period.activities.min"/></th>
		<td><acme:print value="${statisticsPeriodActivitiesWorkbook.Min}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.period.activities.max"/></th>
		<td><acme:print value="${statisticsPeriodActivitiesWorkbook.Max}"/></td>
	</tr>
	<tr>
	<th><h2><acme:message code="student.dashboard.form.learning.time.courses.enrolled"/></h2></th>
	</tr>
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.learning.time.courses.enrolled.avg"/></th>
		<td><acme:print value="${statisticsLearningTimeCoursesEnrolled.Avg}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.learning.time.courses.enrolled.deviation"/></th>
		<td><acme:print value="${statisticsLearningTimeCoursesEnrolled.Deviation}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.learning.time.courses.enrolled.min"/></th>
		<td><acme:print value="${statisticsLearningTimeCoursesEnrolled.Min}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="student.dashboard.form.learning.time.courses.enrolled.max"/></th>
		<td><acme:print value="${statisticsLearningTimeCoursesEnrolled.Max}"/></td>
	</tr>	
</table>
<acme:return/>