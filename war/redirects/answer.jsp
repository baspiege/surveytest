<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page isELIgnored="false" %>
<c:redirect url="/answer">
  <c:param name="answerId" value="${answerId}"/>
  <c:param name="reload" value="true"/>
</c:redirect>