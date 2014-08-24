<%-- This JSP has the HTML for question add page. --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<fmt:bundle basename="Text">
<title><fmt:message key="addLanguageLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">
<jsp:include page="/pages/components/edits.jsp"/>
<%-- Fields --%>
<form id="language" method="post" action="languageAdd" autocomplete="off">
<table>
<tr><td><fmt:message key="nameLabel"/>:</td><td><input type="text" name="name" value="<c:out value="${language.name}"/>" id="name" title="<fmt:message key="nameLabel"/>" maxlength="500"/></td></tr>
</table>
<p>
<input id="storeId" type="hidden" name="surveyId" value="<c:out value="${language.surveyId}"/>" />
<input class="button" type="submit" style="display:none" id="addButtonDisabled" disabled="disabled" value="<fmt:message key="addLabel"/>"/>
<input class="button" type="submit" style="display:inline" id="addButtonEnabled" name="action" value="<fmt:message key="addLabel"/>"/>
</p>
</form>
<jsp:include page="/pages/components/footer.jsp"/>
</body>
</fmt:bundle>
<script type="text/javascript" src="/js/languageAdd.js" ></script>
</html>