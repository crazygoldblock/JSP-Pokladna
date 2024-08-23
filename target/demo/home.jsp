<%@page import="Core.Utils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%

String email = (String)session.getAttribute("email");
String heslo = (String)session.getAttribute("heslo");

if (!Utils.ValidateLogin(email, heslo)) {
        response.sendRedirect("login.html");
}

%>

<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
	<link href="1E.css" rel="stylesheet">
</head>
<body>
	<nav>
		<a href="home.jsp">Hlavní stránka</a>
		<ul class="menu">
		<li><a href="mods.jsp">Moderátoři</a></li>   
		<li><a href="roleslist.jsp">Upravení rolí</a></li>    
		<li><a href="logout.jsp">Odhlásit se</a></li>            
		</ul>
	</nav>
	
	<img src="javaLogo.png" alt="obrázek"  width=600px; height=400px;>
    
</body>
</html>
