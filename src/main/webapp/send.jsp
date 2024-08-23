<%@page import="Core.Utils"%>
<%@page import="Core.Polozka"%>

<%
    Utils.SaveOrder((String)request.getParameter("order"));

    response.sendRedirect("order.jsp");
%>