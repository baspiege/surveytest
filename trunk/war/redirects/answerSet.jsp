<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page isELIgnored="false" %>
<c:redirect url="/answerSet">
  <c:param name="answerSetId" value="${answerSetId}"/>
  <c:param name="reload" value="true"/>
</c:redirect>