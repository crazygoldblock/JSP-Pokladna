<%@page import="Core.Utils"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%

String email = (String)session.getAttribute("email");
String heslo = (String)session.getAttribute("heslo");

if (!Utils.ValidateLogin(email, heslo)) {
        response.sendRedirect("login.html");
}

if (!Utils.MaUzivatelRoli((String)session.getAttribute("email"), 3)) {
        response.sendRedirect("home.jsp");
}

%>

<%  

String email = request.getParameter("email");
String role = request.getParameter("role");
String akce = request.getParameter("akce");

if (akce.equals("1")) {
    Utils.PridatRoli(email, role);
}
else {
    Utils.OdebratRoli(email, role);
}

response.sendRedirect("roleslist.jsp");

%>

