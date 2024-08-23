<%@page import="Core.Login"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%

String email = (String)session.getAttribute("email");
String heslo = (String)session.getAttribute("heslo");

if (!Login.ValidateLogin(email, heslo)) {
        response.sendRedirect("login.html");
}

%>

<%

String email = request.getParameter("user");
String[] role = Login.GetRoleUzivatele(email);

for (int i = 0; i < role.length; i++) { 
    out.println(role[i]);
}

%>


