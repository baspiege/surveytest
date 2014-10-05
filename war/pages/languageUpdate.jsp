<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<fmt:bundle basename="Text">
<title><fmt:message key="updateLanguageLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">
<jsp:include page="/pages/components/edits.jsp"/>
<%-- Fields --%>
<form id="language" method="post" action="languageUpdate" autocomplete="off">
<table>
  <tr>
    <td><fmt:message key="nameLabel"/></td>
    <td><input type="text" name="name" value="<c:out value="${language.name}"/>" id="name" title="<fmt:message key="nameLabel"/>" maxlength="500"/></td>
  </tr>
  <tr>
    <td><fmt:message key="introTextLabel"/></td>
    <td><input type="text" name="name" value="<c:out value="${language.introText}"/>" id="introText" title="<fmt:message key="introTextLabel"/>" maxlength="500"/></td>
  </tr>
  <tr>
    <td><fmt:message key="confirmationTextLabel"/></td>
    <td><input type="text" name="name" value="<c:out value="${language.confirmationText}"/>" id="confirmationText" title="<fmt:message key="confirmationTextLabel"/>" maxlength="500"/></td>
  </tr>
  <tr>
    <td><fmt:message key="submitButtonTextLabel"/></td>
    <td><input type="text" name="name" value="<c:out value="${language.submitButtonText}"/>" id="submitButtonText" title="<fmt:message key="submitButtonTextLabel"/>" maxlength="500"/></td>
  </tr>
</table>
<p>
<input id="surveyId" type="hidden" name="surveyId" value="<c:out value="${language.surveyId}"/>" />
<input id="languageId" type="hidden" name="languageId" value="<c:out value="${language.key.id}"/>" />
<input class="button" type="submit" style="display:none" id="updateButtonDisabled" disabled="disabled" value="<fmt:message key="updateLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="updateButtonEnabled" name="action" value="<fmt:message key="updateLabel"/>"/>
</p>
</form>
<jsp:include page="/pages/components/footer.jsp"/>
</body>
</fmt:bundle>
<script type="text/javascript" src="/js/languageUpdate.js" ></script>
</html>