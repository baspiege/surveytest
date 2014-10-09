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
    <li><c:out value="${survey.name}"/></li>
  </ul>
</nav>

<jsp:include page="/pages/components/edits.jsp"/>

<form method="post" action="surveyLanguage" autocomplete="off">

<section>
<ul class="noListMarkers">
  <c:forEach var="language" items="${languages}">
    <li>
      <input type="radio" name="languageId" id="language_<c:out value="${language.key.id}"/>" value="<c:out value="${language.key.id}"/>">
      <label for="language_<c:out value="${language.key.id}"/>"> <c:out value="${language.name}"/> </label> 
    </li>
  </c:forEach>
</ul>
</section>

<br/>
<input id="surveyId" type="hidden" name="surveyId" value="<c:out value="${survey.key.id}"/>" />
<input class="button" type="submit" style="display:none" id="addButtonDisabled" disabled="disabled" value="<fmt:message key="submitLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="addButtonEnabled" name="action" value="<fmt:message key="submitLabel"/>"/>

</form>

<jsp:include page="/pages/components/footer.jsp"/>

</body>
</fmt:bundle>
<script type="text/javascript" src="/js/surveyLanguage.js" ></script>
</html>