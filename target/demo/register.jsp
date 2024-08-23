<%@page import="Core.Utils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%  

String jmeno = request.getParameter("jmeno");
String email = request.getParameter("email");
String heslo = request.getParameter("heslo");

boolean valid = true;

if (!Utils.CheckEmail(email))
    valid = false;

if (!Utils.isValidEmail(email))
    valid = false;

if (!Utils.isValidPassword(heslo))
    valid = false;

if (valid) {
Utils.NewUser(jmeno, email, heslo);

session.setAttribute("email", email);  
session.setAttribute("heslo", heslo);  

response.sendRedirect("home.jsp");
}
else {
    response.sendRedirect("register.html");
}



%>

