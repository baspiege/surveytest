<%-- This JSP has the HTML for survey page. --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<fmt:bundle basename="Text">
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<title id="title"><fmt:message key="surveyLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">

<nav>
<ul id="navlist">
<li><a href="/surveys"><fmt:message key="mainLabel"/></a></li>
<li id="offline" style="display:none"><fmt:message key="offlineLabel"/></li>
</ul>
</nav>

<jsp:include page="/pages/components/edits.jsp"/>

<section>
<span id="languageAdd"></span> 
<a href="/languageAdd?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="addLanguageLabel"/></a> 
<h2> <fmt:message key="languagesLabel"/> </h2>
<ul>
<c:forEach var="language" items="${languages}">
  <li><c:out value="${language.name}"/></li>
</c:forEach>
</ul>
</section>

<section>
<span id="questionAdd"></span> 
<a href="/questionAdd?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="addQuestionLabel"/></a> 
<h2> <fmt:message key="questionsLabel"/> </h2>
<ul>
<c:forEach var="question" items="${questions}">
  <li><c:out value="${question.name}"/></li>
</c:forEach>
</ul>
</section>

<jsp:include page="/pages/components/footer.jsp"/>

</body>
</fmt:bundle>
<script type="text/javascript" src="/js/surveys.js" ></script>
</html>