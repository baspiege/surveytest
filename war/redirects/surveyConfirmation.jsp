<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page isELIgnored="false" %>
<c:redirect url="/surveyConfirmation">
  <c:param name="surveyId" value="${surveyId}"/>
  <c:param name="languageId" value="${languageId}"/>
  <c:param name="rewardId" value="${rewardId}"/>
  <c:param name="token" value="${token}"/>
  <c:param name="reload" value="true"/>
</c:redirect>