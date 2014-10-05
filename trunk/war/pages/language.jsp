<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/pages/components/htmlStartNoCache.jsp" />
<title id="title"></title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body onunload="">
<fmt:bundle basename="Text">

<nav>
<ul id="navlist">
<li><a href="/surveys"><fmt:message key="mainLabel"/></a></li>
<li><a href="/survey?surveyId=<c:out value="${survey.key.id}"/>"><c:out value="${survey.name}"/></a></li>
</ul>
</nav>

<jsp:include page="/pages/components/edits.jsp"/>

<section class="data">
<a href="/languageUpdate?languageId=<c:out value="${language.key.id}"/>" class="edit" id="languageEditLink"><fmt:message key="editLabel"/></a> 
<table>
  <tr>
    <td>
      <fmt:message key="languageLabel"/>
    </td>
    <td>
      <c:out value="${language.name}"/>
    </td>
  </tr>

  <tr>
    <td>
      <fmt:message key="introTextLabel"/>
    </td>
    <td>
      <c:out value="${language.introText}"/>
    </td>
  </tr>
  
  <tr>
    <td>
      <fmt:message key="confirmationTextLabel"/>
    </td>
    <td>
      <c:out value="${language.confirmationText}"/>
    </td>
  </tr>
  
  <tr>
    <td>
      <fmt:message key="submitButtonTextLabel"/>
    </td>
    <td>
      <c:out value="${language.submitButtonText}"/>
    </td>
  </tr>

</table>

</section>

<jsp:include page="/pages/components/footer.jsp"/>
</fmt:bundle>
</body>
</html>