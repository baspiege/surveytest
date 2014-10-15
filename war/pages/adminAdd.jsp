<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<fmt:bundle basename="Text">
<title><fmt:message key="addAdminLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">
<jsp:include page="/pages/components/edits.jsp"/>
<form method="post" action="adminAdd" autocomplete="off">
<table>
  <tr>
    <td><fmt:message key="userIdLabel"/></td>
    <td><input type="text" name="url" value="<c:out value="${admin.userId}"/>" id="name" title="<fmt:message key="userIdLabel"/>" maxlength="500"/></td>
  </tr>
</table>
<p>
<input id="surveyId" type="hidden" name="surveyId" value="<c:out value="${admin.surveyId}"/>" />
<input class="button" type="submit" style="display:none" id="addButtonDisabled" disabled="disabled" value="<fmt:message key="addLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="addButtonEnabled" name="action" value="<fmt:message key="addLabel"/>"/>
</p>
</form>
<jsp:include page="/pages/components/footer.jsp"/>
</body>
</fmt:bundle>
<script type="text/javascript" src="/js/adminAdd.js" ></script>
</html>