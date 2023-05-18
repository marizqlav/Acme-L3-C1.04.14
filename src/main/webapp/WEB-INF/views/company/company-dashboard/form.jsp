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

<h2>
	<acme:message code="company.dashboard.form.title.general-indicators"/>
</h2>

<h3>
	<acme:message code="company.dashboard.form.title.statisticsPeriodLengthOfTheirPractica"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.statisticsPeriodLengthOfTheirPractica.average"/>
		</th>
		<td>
			<acme:print value="${statisticsPeriodLengthOfTheirPractica.AVERAGE}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.statisticsPeriodLengthOfTheirPractica.minimum"/>
		</th>
		<td>
			<acme:print value="${statisticsPeriodLengthOfTheirPractica.MIN}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.statisticsPeriodLengthOfTheirPractica.maximum"/>
		</th>
		<td>
			<acme:print value="${statisticsPeriodLengthOfTheirPractica.MAX}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.statisticsPeriodLengthOfTheirPractica.deviation"/>
		</th>
		<td>
			<acme:print value="${statisticsPeriodLengthOfTheirPractica.DEVIATION}"/>
		</td>
	</tr>
</table>

<h3>
	<acme:message code="company.dashboard.form.title.statisticsPeriodLengthOfTheSessionsInTheirPractica"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.statisticsPeriodLengthOfTheSessionsInTheirPractica.average"/>
		</th>
		<td>
			<acme:print value="${statisticsPeriodLengthOfTheSessionsInTheirPractica.AVERAGE}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.statisticsPeriodLengthOfTheSessionsInTheirPractica.minimum"/>
		</th>
		<td>
			<acme:print value="${statisticsPeriodLengthOfTheSessionsInTheirPractica.MIN}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.statisticsPeriodLengthOfTheSessionsInTheirPractica.maximum"/>
		</th>
		<td>
			<acme:print value="${statisticsPeriodLengthOfTheSessionsInTheirPractica.MAX}"/>
		</td>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.statisticsPeriodLengthOfTheSessionsInTheirPractica.deviation"/>
		</th>
		<td>
			<acme:print value="${statisticsPeriodLengthOfTheSessionsInTheirPractica.DEVIATION}"/>
		</td>
	</tr>
</table>


<h2>
	<acme:message code="company.dashboard.form.title.nPracticumByMonthLastYear"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
	Total ${nPracticumByMonthLastYear.get('FEBRUARY')}
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${nPracticumByMonthLastYear.get('JANUARY')}"/>,
						<jstl:out value="${nPracticumByMonthLastYear.get('FEBRUARY')}"/>,
						<jstl:out value="${nPracticumByMonthLastYear.get('MARCH')}"/>,
						<jstl:out value="${nPracticumByMonthLastYear.get('APRIL')}"/>,
						<jstl:out value="${nPracticumByMonthLastYear.get('MAY')}"/>,
						<jstl:out value="${nPracticumByMonthLastYear.get('JUNE')}"/>,
						<jstl:out value="${nPracticumByMonthLastYear.get('JULY')}"/>,
						<jstl:out value="${nPracticumByMonthLastYear.get('AUGUST')}"/>,
						<jstl:out value="${nPracticumByMonthLastYear.get('SEPTEMBER')}"/>,
						<jstl:out value="${nPracticumByMonthLastYear.get('OCTOBER')}"/>,
						<jstl:out value="${nPracticumByMonthLastYear.get('NOVEMBER')}"/>,
						<jstl:out value="${nPracticumByMonthLastYear.get('DECEMBER')}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>
