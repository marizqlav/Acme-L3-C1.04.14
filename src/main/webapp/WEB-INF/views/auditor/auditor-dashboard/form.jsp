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

<h1><acme:message code="auditor.dashboard.title"/></h1>
<table class="table table-sm">
	<tr>
	<th><h2><acme:message code="auditor.dashboard.form.total.numer.type"/></h2></th>
	</tr>
	<tr>
		<th scope="row"><acme:message code="auditor.dashboard.form.label.total.number.theory"/></th>
		<td><acme:print value="${nTheoryAudits}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message code="auditor.dashboard.form.label.total.number.handson"/></th>
		<td><acme:print value="${nHandsOnAudits}"/></td>
	</tr>
	<tr>
	<th><h2><acme:message code="auditor.dashboard.form.auditingrecord.statistics"/></h2></th>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="auditor.dashboard.form.auditingrecord.statistics.avg"/></th>
		<td><acme:print value="${auditingRecordsStatistics.AVERAGE}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="auditor.dashboard.form.auditingrecord.statistics.deviation"/></th>
		<td><acme:print value="${auditingRecordsStatistics.DEVIATION}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="auditor.dashboard.form.auditingrecord.statistics.min"/></th>
		<td><acme:print value="${auditingRecordsStatistics.MIN}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="auditor.dashboard.form.auditingrecord.statistics.max"/></th>
		<td><acme:print value="${auditingRecordsStatistics.MAX}"/></td>
	</tr>
	<tr>
	<th><h2><acme:message code="auditor.dashboard.form.auditingrecordtime.statistics"/></h2></th>
	</tr>
	<tr>
		<th scope="row"><acme:message code="auditor.dashboard.form.auditingrecordtime.statistics.avg"/></th>
		<td><acme:print value="${periodOfAuditingRecordsStatistics.AVERAGE}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="auditor.dashboard.form.auditingrecordtime.statistics.deviation"/></th>
		<td><acme:print value="${periodOfAuditingRecordsStatistics.DEVIATION}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="auditor.dashboard.form.auditingrecordtime.statistics.min"/></th>
		<td><acme:print value="${periodOfAuditingRecordsStatistics.MIN}"/></td>
	</tr>	
	<tr>
		<th scope="row"><acme:message code="auditor.dashboard.form.auditingrecordtime.statistics.max"/></th>
		<td><acme:print value="${periodOfAuditingRecordsStatistics.MAX}"/></td>
	</tr>	
</table>
<acme:return/>
