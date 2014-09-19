<%-- This JSP has the HTML for answer add page. --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<fmt:bundle basename="Text">
<title><fmt:message key="addAnswerLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">
<jsp:include page="/pages/components/edits.jsp"/>
<%-- Fields --%>
<form id="answer" method="post" action="answerAdd" autocomplete="off">
<table>
  <tr>
    <td>
      <fmt:message key="descriptionLabel"/>
    </td>
    <td>
      <textarea name="description" title="<fmt:message key="descriptionLabel"/>" maxlength="500"/><c:out value="${answer.text}"/></textarea>
    </td>
  </tr>

  <c:forEach var="answerText" items="${answerTexts}">
    <tr>
      <td>
        <c:out value="${answerText.language.name}"/>
      </td>
      <td>
        <textarea name="answerText_Language_<c:out value="${answerText.language.key.id}"/>" title="<fmt:message key="descriptionLabel"/>" maxlength="500"/><c:out value="${answerText.text}"/></textarea>
      </td>
    </tr>
  </c:forEach>
  
</table>
<p>
<input id="answerSetId" type="hidden" name="answerSetId" value="<c:out value="${answerSet.key.id}"/>" />
<input class="button" type="submit" style="display:none" id="addButtonDisabled" disabled="disabled" value="<fmt:message key="addLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="addButtonEnabled" name="action" value="<fmt:message key="addLabel"/>"/>
</p>
</form>
<jsp:include page="/pages/components/footer.jsp"/>
</body>
</fmt:bundle>
<script type="text/javascript" src="/js/answerAdd.js" ></script>
</html>