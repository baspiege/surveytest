<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page isELIgnored="false" %>
<c:redirect url="/reward">
  <c:param name="rewardId" value="${rewardId}"/>
  <c:param name="reload" value="true"/>
</c:redirect>