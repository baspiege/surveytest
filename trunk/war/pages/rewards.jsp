<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<fmt:bundle basename="Text">
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<title id="title"><fmt:message key="rewardsLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">

<nav>
  <ul id="navlist">
    <li><a href="/surveys"><fmt:message key="mainLabel"/></a></li>
    <li><c:out value="${survey.name}"/></li>
  </ul>
</nav>

<jsp:include page="/pages/components/edits.jsp"/>

<section>
<p> <fmt:message key="rewardsLabel"/> <a class="add" href="/rewardAdd?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="addLabel"/></a> </p>
<ul>
  <c:forEach var="reward" items="${rewards}">
    <li>
      <a href="/reward?rewardId=<c:out value="${reward.key.id}"/>"><c:out value="${reward.url}"/></a>        
    </li>
  </c:forEach>
</ul>
</section>

<jsp:include page="/pages/components/footer.jsp"/>

</body>
</fmt:bundle>
</html>