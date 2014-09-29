<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page isELIgnored="false" %>
<c:redirect url="/survey">
  <c:param name="surveyId" value="${surveyId}"/>
  <c:param name="reload" value="true"/>
</c:redirect>