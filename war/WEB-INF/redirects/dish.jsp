<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page isELIgnored="false" %>
<c:redirect url="/stores">
  <c:param name="questionId" value="${questionId}"/>
  <c:param name="reload" value="true"/>
</c:redirect>