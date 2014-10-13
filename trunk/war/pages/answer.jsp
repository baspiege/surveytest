<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<title id="title"></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">
<fmt:bundle basename="Text">

<nav>
<ul id="navlist">
<li><a href="/surveys"><fmt:message key="mainLabel"/></a></li>
<li><a href="/survey?surveyId=<c:out value="${survey.key.id}"/>"><c:out value="${survey.name}"/></a></li>
<li><a href="/answerSet?answerSetId=<c:out value="${answerSet.key.id}"/>"><c:out value="${answerSet.description}"/></a></li>
</ul>
</nav>

<jsp:include page="/pages/components/edits.jsp"/>

<section class="data">
<a href="/answerUpdate?answerId=<c:out value="${answer.key.id}"/>" class="edit" id="answerEditLink"><fmt:message key="editLabel"/></a> 
<table>
<tr>
  <td>
    <fmt:message key="answerLabel"/>
  </td>
  <td>
    <c:out value="${answer.text}"/>
  </td>
</tr>

<c:forEach var="answerText" items="${answerTexts}">
  <tr>
    <td>
      <c:out value="${answerText.language.name}"/>
    </td>
    <td>
      <c:out value="${answerText.text}"/>
    </td>
  </tr>
</c:forEach>
</table>

</section>

<jsp:include page="/pages/components/footer.jsp"/>
</fmt:bundle>
</body>
</html>