<%--
- footer.jsp
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:footer-panel>
	<acme:footer-subpanel code="master.footer.title.about">
		<acme:footer-option icon="fa fa-building" code="master.footer.label.company" action="/master/company"/>
		<acme:footer-option icon="fa fa-file" code="master.footer.label.license" action="/master/license"/>		
	</acme:footer-subpanel>

	<acme:footer-subpanel code="master.footer.title.social">
		<acme:message var="$linkedin$url" code="master.footer.url.linkedin"/>
		<acme:footer-option icon="fab fa-linkedin" code="master.footer.label.linked-in" action="${$linkedin$url}" newTab="true"/>
		<acme:message var="$twitter$url" code="master.footer.url.twitter"/>
		<acme:footer-option icon="fab fa-twitter" code="master.footer.label.twitter" action="${$twitter$url}" newTab="true"/>
	</acme:footer-subpanel>

	<acme:footer-subpanel code="master.footer.title.languages">
		<acme:footer-option icon="fa fa-language" code="master.footer.label.english" action="/?locale=en"/>
		<acme:footer-option icon="fa fa-language" code="master.footer.label.spanish" action="/?locale=es"/>
	</acme:footer-subpanel>

	<acme:footer-logo logo="images/logo.png">
		<acme:footer-copyright code="master.company.name"/>
	</acme:footer-logo>

</acme:footer-panel>

<br>
<br>

<jstl:if test="${banner != null}">

	<div style="width: 100%; flex-direction: row; display: flex; justify-content: center;">
		<div style="position: relative; width: 100%;">

			<jstl:if test="${banner.linkPicture != null}">
				<img id="banner-img" src="${banner.linkPicture}" style="width: 100%; min-height: 300px; object-fit: cover;"/>
			</jstl:if>
			<jstl:if test="${banner.linkPicture == null}">
				<img id="banner-img" src="images/banner.png" style="width: 100%; min-height: 300px; object-fit: cover;"/>
			</jstl:if>

			<div style="position: absolute; bottom: 10%; width: 100%;">
				<div id="banner-text-box" style="margin-left: 15%; width: 70%; background-color: rgba(0, 0, 0, 0.4);">
					<h1 style='color: white; font-size: 30px; text-align: center; word-break: break-all;'>${banner.slogan}</h1>
					<a style='color: white; font-size: 20px; text-align: center; display: block;' href='${banner.linkWeb}'><acme:message code="footer.banner.link"/></a>
				</div>
			</div>
		</div>
	</div>

</jstl:if>

<br>
<br>
