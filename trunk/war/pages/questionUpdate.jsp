<%-- This JSP has the HTML for question update page. --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<fmt:bundle basename="Text">
<title><fmt:message key="questionLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body>
<jsp:include page="/pages/components/edits.jsp"/>
<form id="question" method="post" action="questionUpdate" autocomplete="off">
<table>
<tr><td><fmt:message key="nameLabel"/>:</td><td><input type="text" name="note" value="<c:out value="${question.note}"/>" id="note" title="<fmt:message key="nameLabel"/>" maxlength="500"/></td></tr>
<tr><td><fmt:message key="lastUpdatedLabel"/>:</td><td><fmt:formatDate pattern="yyyy MMM dd h:mm aa zzz" timeZone="America/Chicago" value="${question.lastUpdateTime}"/></td></tr>
</table>
<section>
<input type="hidden" name="questionId" value="<c:out value="${question.key.id}"/>"/>
<input class="button" type="submit" name="action" value="<fmt:message key="updateLabel"/>"/>
<input class="button" type="submit" name="action" value="<fmt:message key="deleteLabel"/>"/>
</section>
</form>
<jsp:include page="/pages/components/footer.jsp"/>
</body>
</fmt:bundle>
</html>