<%@page import="Core.Utils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%  

String email = request.getParameter("email");
String heslo = request.getParameter("heslo");

if (Utils.ValidateLogin(email, heslo)) {
    session.setAttribute("email", email);  
    session.setAttribute("heslo", heslo);  

    response.sendRedirect("home.jsp");
}
else {
    response.sendRedirect("login.html");
}

%>



