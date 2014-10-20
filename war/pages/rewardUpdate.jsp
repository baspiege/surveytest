<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<fmt:bundle basename="Text">
<title><fmt:message key="updateRewardLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">
<jsp:include page="/pages/components/edits.jsp"/>
<form method="post" action="rewardUpdate" autocomplete="off">
<table>
  <tr>
    <td><fmt:message key="urlLabel"/></td>
    <td><input type="text" name="url" value="<c:out value="${reward.url}"/>" id="name" title="<fmt:message key="urlLabel"/>" maxlength="500"/></td>
  </tr>
</table>
<p>
<input id="rewardId" type="hidden" name="rewardId" value="<c:out value="${reward.key.id}"/>" />
<input class="button" type="submit" style="display:none" id="updateButtonDisabled" disabled="disabled" value="<fmt:message key="updateLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="updateButtonEnabled" name="action" value="<fmt:message key="updateLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="deleteButtonEnabled" name="action" value="<fmt:message key="deleteLabel"/>"/>
</p>
</form>
<jsp:include page="/pages/components/footer.jsp"/>
</body>
</fmt:bundle>
<script type="text/javascript" src="/js/rewardUpdate.js" ></script>
</html>