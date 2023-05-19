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
	<acme:input-textbox code="authenticated.offer.form.label.instantiationMoment" readonly="true" path="instantiationMoment"/>	
	<acme:input-textbox code="authenticated.offer.form.label.heading" readonly="true" path="heading"/>
	<acme:input-textbox code="authenticated.offer.form.label.summary" readonly="true" path="summary"/>
	<acme:input-textbox code="authenticated.offer.form.label.availabilityPeriodInitial" readonly="true" path="availabilityPeriodInitial"/>
	<acme:input-textbox code="authenticated.offer.form.label.availabilityPeriodFinal" readonly="true" path="availabilityPeriodFinal"/>
	<acme:input-textbox code="authenticated.offer.form.label.price"  readonly="true" path="price"/>
	<acme:input-money code="authenticated.offer.form.label.retail.price.exchange.money" readonly="true" path="exchangeMoney"/>
	<jstl:if test="${link != null && !link.isEmpty()}">
		<acme:input-url code="authenticated.offer.form.label.link" readonly="true" path="link"/>
	</jstl:if>
</acme:form>
