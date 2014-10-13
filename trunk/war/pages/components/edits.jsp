<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<fmt:bundle basename="Text">
<c:if test="${edits!=null && !empty edits}">
<section class="edits">
  <b><fmt:message key="requestNotProcessedEditLabel"/></b>
  <ul>
  <c:forEach var="edit" items="${edits}">
    <li><c:out value="${edit}" /></li>
  </c:forEach>
  </ul>
</section>
</c:if>
</fmt:bundle>