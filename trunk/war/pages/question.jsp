<%-- This JSP has the HTML for question page. --%>
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
</ul>
</nav>

<jsp:include page="/pages/components/edits.jsp"/>

<section>

<span id="questionName"></span> 
<a href="/questionUpdate?questionId=<c:out value="${question.key.id}"/>" class="edit" style="display:none" id="questionEditLink"><fmt:message key="editLabel"/></a> 

<section class="data">

<table>
<tr>
  <td>
    <fmt:message key="descriptionLabel"/>
  </td>
  <td>
    <c:out value="${question.text}"/>
  </td>
</tr>

<c:forEach var="questionText" items="${questionTexts}">
  <tr>
    <td>
      <c:out value="${questionText.language.name}"/>
    </td>
    <td>
      <c:out value="${questionText.text}"/>
    </td>
  </tr>
</c:forEach>
</table>

</section>

<jsp:include page="/pages/components/footer.jsp"/>
</fmt:bundle>
</body>
<script type="text/javascript" src="/js/question.js" ></script>
</html>