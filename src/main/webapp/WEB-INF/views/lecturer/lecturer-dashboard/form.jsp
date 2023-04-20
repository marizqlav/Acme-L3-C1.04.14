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

<h1><acme:message code="lecturer.dashboard.title"/></h1>
<table class="table table-sm">
	<tr>
	<th><h2><acme:message code="lecturer.dashboard.form.total.numer.type"/></h2></th>
	</tr>
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.label.total.number.theory"/></th>
		<td><acme:print value="${nTheoryLectures}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.label.total.number.handson"/></th>
		<td><acme:print value="${nHandsOnLectures}"/></td>
	</tr>
	<tr>
	<th><h2><acme:message code="lecturer.dashboard.form.lecture.statistics"/></h2></th>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.lecture.statistics.avg"/></th>
		<td><acme:print value="${statisticsLecture.AVERAGE}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.lecture.statistics.deviation"/></th>
		<td><acme:print value="${statisticsLecture.DEVIATION}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.lecture.statistics.min"/></th>
		<td><acme:print value="${statisticsLecture.MIN}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.lecture.statistics.max"/></th>
		<td><acme:print value="${statisticsLecture.MAX}"/></td>
	</tr>
	<tr>
	<th><h2><acme:message code="lecturer.dashboard.form.course.statistics"/></h2></th>
	</tr>
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.course.statistics.avg"/></th>
		<td><acme:print value="${statisticsCourses.AVERAGE}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.course.statistics.deviation"/></th>
		<td><acme:print value="${statisticsCourses.DEVIATION}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.course.statistics.min"/></th>
		<td><acme:print value="${statisticsCourses.MIN}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="lecturer.dashboard.form.course.statistics.max"/></th>
		<td><acme:print value="${statisticsCourses.MAX}"/></td>
	</tr>	
</table>
<acme:return/>
