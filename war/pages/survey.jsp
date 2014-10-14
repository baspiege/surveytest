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
    <li><c:out value="${survey.name}"/></li>
  </ul>
</nav>

<jsp:include page="/pages/components/edits.jsp"/>

<section>
<p> <fmt:message key="actionsLabel"/> </p>
<ul>
    <li><a href="/surveyUpdate?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="editLabel"/></a></li> 
    <li><a href="/admins?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="manageAdminsLabel"/></a></li>
    <li><a href="/rewards?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="manageRewardsLabel"/></a></li>
    <li><a href="/surveyLanguage?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="previewLabel"/></a></li>
    <li><a href="/surveyResponses?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="viewResponsesLabel"/></a></li>
</ul>
</section>

<section>
<p> <fmt:message key="languagesLabel"/> <a class="add" href="/languageAdd?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="addLabel"/></a> </p>
<ul>
  <c:forEach var="language" items="${languages}">
    <li>
      <a href="/language?languageId=<c:out value="${language.key.id}"/>"><c:out value="${language.name}"/></a>        
    </li>
  </c:forEach>
</ul>
</section>

<section class="data">
<fmt:message key="questionsLabel"/> <a class="add" href="/questionAdd?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="addLabel"/></a>
<ul>
  <c:forEach var="question" items="${questions}">
    <li>
      <a href="/question?questionId=<c:out value="${question.key.id}"/>"><c:out value="${question.text}"/></a>        
    </li>
  </c:forEach>
</ul>
</section>

<section class="data">
<fmt:message key="answerSetsLabel"/> <a class="add" href="/answerSetAdd?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="addLabel"/></a>
<ul>
  <c:forEach var="answerSet" items="${answerSets}">
    <li>
      <a href="/answerSet?answerSetId=<c:out value="${answerSet.key.id}"/>"><c:out value="${answerSet.description}"/></a>
    </li>
  </c:forEach>
</ul>
</section>

<jsp:include page="/pages/components/footer.jsp"/>

</body>
</fmt:bundle>
</html>