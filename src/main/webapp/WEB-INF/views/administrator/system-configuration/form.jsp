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
	<acme:input-textarea code="administrator.config.form.label.currencies" path="listOfAcceptedCurrencies" readonly="true"/>
	<jstl:if test="${_command!='add'}">
		<acme:input-textarea code="administrator.config.form.label.currency" path="systemCurrency"/>
	</jstl:if>
	<jstl:if test="${_command=='add'}">
		<acme:hidden-data path="systemCurrency"/>
		<acme:input-textarea code="administrator.config.form.label.currency.new" path="newCurrency"/>
	</jstl:if>
	
	<acme:submit test="${_command!='add'}" code="administrator.config.form.button.update" action="/administrator/system-configuration/update"/>
	<acme:button test="${_command!='add'}" code="administrator.config.form.button.add" action="/administrator/system-configuration/add"/>
	<acme:submit test="${_command=='add'}" code="administrator.config.form.button.add" action="/administrator/system-configuration/add"/>	
</acme:form>
