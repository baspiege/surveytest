<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<fmt:bundle basename="Text">
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<title id="title"><fmt:message key="surveyLabel"/></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">

<nav>
  <ul id="navlist">
    <li><c:out value="${survey.name}"/></li>
  </ul>
</nav>

<jsp:include page="/pages/components/edits.jsp"/>

<form method="post" action="surveyResponse" autocomplete="off">

<p>
  <c:out value="${language.confirmationText}"/>
</p>

<jsp:include page="/pages/components/footer.jsp"/>

</body>
</fmt:bundle>
</html>