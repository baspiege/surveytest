<%-- This JSP has the HTML for surveys page. --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/pages/components/htmlStartAppCache.jsp" %>
<title id="title"></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body>
<fmt:bundle basename="Text">

<nav>
<ul id="navlist">
<li><a href="/stores"><fmt:message key="mainLabel"/></a></li>
<li id="offline" style="display:none"><fmt:message key="offlineLabel"/></li>
</ul>
</nav>

<jsp:include page="/WEB-INF/pages/components/edits.jsp"/>

<section>

<span id="addSurvey"></span> 
<a href="/addSurvey"><fmt:message key="addLabel"/></a> 

<%-- Data --%>
<ul>
<c:forEach var="survey" items="${surveys}">
  <li><c:out value="${survey.name}"/></li>
</c:forEach>
</ul>

</section>

<jsp:include page="/WEB-INF/pages/components/footer.jsp"/>
</fmt:bundle>
</body>
<script type="text/javascript" src="/js/question.js" ></script>
</html>