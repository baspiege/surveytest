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

<p> <fmt:message key="languagesLabel"/> <a class="add" href="/languageAdd?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="addLabel"/></a> </p>
<ul>
<c:forEach var="language" items="${languages}">
  <li><c:out value="${language.name}"/></li>
</c:forEach>
</ul>
</section>

<section class="data">
<fmt:message key="questionsLabel"/> <a class="add" href="/questionAdd?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="addLabel"/></a> </p>
<table>
<%-- <th class="data"><fmt:message key="questionLabel"/></th><th><fmt:message key="actionLabel"/></th> --%>

<c:forEach var="question" items="${questions}">
  <tr>
    <td>
        <a href="/question?questionId=<c:out value="${question.key.id}"/>"><c:out value="${question.text}"/></a>
      
    </td>
<%--    <td>
    <a href="/question?questionId=<c:out value="${question.key.id}"/>">Details</a>
    </td>  --%>
  </tr>
</c:forEach>
</table>
</section>

<jsp:include page="/pages/components/footer.jsp"/>

</body>
</fmt:bundle>
<script type="text/javascript" src="/js/surveys.js" ></script>
</html>