<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<fmt:bundle basename="Text">
<title><fmt:message key="updateAdminLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">
<jsp:include page="/pages/components/edits.jsp"/>
<form method="post" action="adminUpdate" autocomplete="off">
<table>
  <tr>
    <td><fmt:message key="userIdLabel"/></td>
    <td><input type="text" name="userId" value="<c:out value="${admin.userId}"/>" id="name" title="<fmt:message key="userIdLabel"/>" maxlength="500"/></td>
  </tr>
</table>
<p>
<input id="adminId" type="hidden" name="adminId" value="<c:out value="${admin.key.id}"/>" />
<input class="button" type="submit" style="display:none" id="updateButtonDisabled" disabled="disabled" value="<fmt:message key="updateLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="updateButtonEnabled" name="action" value="<fmt:message key="updateLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="deleteButtonEnabled" name="action" value="<fmt:message key="deleteLabel"/>"/>
</p>
</form>
<jsp:include page="/pages/components/footer.jsp"/>
</body>
</fmt:bundle>
<script type="text/javascript" src="/js/adminUpdate.js" ></script>
</html>