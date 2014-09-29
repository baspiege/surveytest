<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page isELIgnored="false" %>
<c:redirect url="/surveyResponse">
  <c:param name="surveyId" value="${surveyId}"/>
  <c:param name="languageId" value="${languageId}"/>
  <c:param name="reload" value="true"/>
</c:redirect>