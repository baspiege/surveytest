<%-- This JSP has the HTML for question add page. --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<fmt:bundle basename="Text">
<title><fmt:message key="addQuestionLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">
<jsp:include page="/pages/components/edits.jsp"/>
<%-- Fields --%>
<form id="question" method="post" action="questionAdd" autocomplete="off">
<table>
  <tr>
    <td>
      <fmt:message key="descriptionLabel"/>:
    </td>
    <td>
      <textarea name="note" title="<fmt:message key="descriptionLabel"/>" maxlength="500"/><c:out value="${question.text}"/></textarea>
    </td>
  </tr>

  <c:forEach var="questionText" items="${questionTexts}">
    <tr>
      <td>
        <c:out value="${questionText.language.name}"/>
      </td>
      <td>
        <textarea name="note" title="<fmt:message key="descriptionLabel"/>" maxlength="500"/><c:out value="${questionText.text}"/></textarea>
      </td>
    </tr>
  </c:forEach>
  
</table>
<p>
<input class="button" type="submit" style="display:none" id="addButtonDisabled" disabled="disabled" value="<fmt:message key="addLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="addButtonEnabled" name="action" value="<fmt:message key="addLabel"/>"/>
</p>
</form>
<jsp:include page="/pages/components/footer.jsp"/>
</body>
</fmt:bundle>
<script type="text/javascript" src="/js/questionAdd.js" ></script>
</html>