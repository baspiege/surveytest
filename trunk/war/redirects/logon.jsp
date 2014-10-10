<%@ page language="java"%>
<%@ page import="surveytest.utils.RequestUtils" %>
<% 
    // Invalidate the session to remove all data from session.
    session.invalidate();
    request.getSession(true);

    response.sendRedirect(RequestUtils.getUri(request,"/logon"));
%>