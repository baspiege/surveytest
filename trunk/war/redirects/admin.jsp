<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page isELIgnored="false" %>
<c:redirect url="/admin">
  <c:param name="adminId" value="${adminId}"/>
  <c:param name="reload" value="true"/>
</c:redirect>