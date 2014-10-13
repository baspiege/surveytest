<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<fmt:bundle basename="Text">
<title id="title"><fmt:message key="surveysLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">

<jsp:include page="/pages/components/edits.jsp"/>

<section>

<span id="surveyAdd"></span> 
<a href="/surveyAdd"><fmt:message key="addSurveyLabel"/></a> 

<ul>
<c:forEach var="survey" items="${surveys}">
  <li><a href="/survey?surveyId=<c:out value="${survey.key.id}"/>"><c:out value="${survey.name}"/></a></li>
</c:forEach>
</ul>

</section>

<jsp:include page="/pages/components/footer.jsp"/>
</body>
</fmt:bundle>
</html>