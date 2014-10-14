<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<fmt:bundle basename="Text">
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<title id="title"><fmt:message key="adminsLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">

<nav>
  <ul id="navlist">
    <li><a href="/surveys"><fmt:message key="mainLabel"/></a></li>
    <li><a href="/survey?surveyId=<c:out value="${survey.key.id}"/>"><c:out value="${survey.name}"/></a></li>
  </ul>
</nav>

<jsp:include page="/pages/components/edits.jsp"/>

<section class="data">
<fmt:message key="adminsLabel"/> <a class="add" href="/adminAdd?surveyId=<c:out value="${survey.key.id}"/>"><fmt:message key="addLabel"/></a>
<table>
  <tr>
    <th><fmt:message key="userIdLabel"/></th>
  </tr>
  <c:forEach var="reward" items="${rewards}">
    <tr>
      <td>
        <a href="/admin?adminId=<c:out value="${admin.key.id}"/>"><c:out value="${admin.userId}"/></a>        
      </td>
    </tr>
  </c:forEach>
</table>
</section>

<jsp:include page="/pages/components/footer.jsp"/>

</body>
</fmt:bundle>
</html>