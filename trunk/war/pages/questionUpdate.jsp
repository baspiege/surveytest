<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<fmt:bundle basename="Text">
<title><fmt:message key="updateQuestionLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">
<jsp:include page="/pages/components/edits.jsp"/>
<%-- Fields --%>
<form id="question" method="post" action="questionUpdate" autocomplete="off">
<table>
  <tr>
    <td>
      <fmt:message key="descriptionLabel"/>
    </td>
    <td>
      <textarea name="description" title="<fmt:message key="descriptionLabel"/>" maxlength="500"/><c:out value="${question.text}"/></textarea>
    </td>
  </tr>

  <%-- Question Texts --%>
  <c:forEach var="questionText" items="${questionTexts}">
    <tr>
      <td>
        <c:out value="${questionText.language.name}"/>
      </td>
      <td>
        <textarea name="questionText_Language_<c:out value="${questionText.language.key.id}"/>" title="<fmt:message key="descriptionLabel"/>" maxlength="500"/><c:out value="${questionText.text}"/></textarea>
      </td>
    </tr>
  </c:forEach>

  <%-- Answer Set --%>
  <tr>
    <td>
      <fmt:message key="answerSetLabel"/>
    </td>
    <td>
      <select name="answerSetId">
        <c:forEach var="answerSet" items="${answerSets}">
          <option value="${answerSet.key.id}"><c:out value="${answerSet.description}"/></option>
        </c:forEach>
      <select>
    </td>
  </tr>
  
</table>
<p>
<input id="questionId" type="hidden" name="questionId" value="<c:out value="${question.key.id}"/>" />
<input class="button" type="submit" style="display:none" id="updateButtonDisabled" disabled="disabled" value="<fmt:message key="updateLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="updateButtonEnabled" name="action" value="<fmt:message key="updateLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="deleteButtonEnabled" name="action" value="<fmt:message key="deleteLabel"/>"/>
</p>
</form>
<jsp:include page="/pages/components/footer.jsp"/>
</body>
</fmt:bundle>
<script type="text/javascript" src="/js/languageUpdate.js" ></script>
</html>