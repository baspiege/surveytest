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
    <li><c:out value="${survey.name}"/></li>
  </ul>
</nav>

<jsp:include page="/pages/components/edits.jsp"/>

<form id="surveyReponse" method="post" action="surveyResponse" autocomplete="off">

<section class="data">

<table>
  <c:forEach var="question" items="${questions}">
    <tr>
      <td>
        <c:out value="${question.questionTextMap[language.key.id].text}"/>
      </td>
      <td>
        <ul class="noListMarkers">
          <c:forEach var="answer" items="${question.answerSet.answers}">
            <li>
              <c:set var="answerElementId" value="question_${question.key.id}_answer_${answer.key.id}" />
              <input type="radio" name="question_<c:out value="${question.key.id}"/>" id="<c:out value="${answerElementId}"/>" value="<c:out value="${answer.key.id}"/>">
              <label for="<c:out value="${answerElementId}"/>"> <c:out value="${answer.answerTextMap[language.key.id].text}"/> </label> 
            </li>
            
          </c:forEach>
      </ul>

      </td>
    </tr>
  </c:forEach>
</table>


</section>

<br/>
<input class="button" type="submit" style="display:none" id="addButtonDisabled" disabled="disabled" value="<fmt:message key="submitLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="addButtonEnabled" name="action" value="<fmt:message key="submitLabel"/>"/>

</form>


<jsp:include page="/pages/components/footer.jsp"/>

</body>
</fmt:bundle>
<script type="text/javascript" src="/js/surveys.js" ></script>
</html>