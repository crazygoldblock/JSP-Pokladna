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
	<link href="css.css" rel="stylesheet">
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
	


    <%
    
    if (Utils.MaUzivatelRoli((String)session.getAttribute("email"), 2)) {
        out.println("<img src=\"amongUs.png\" alt=\"obrázek\"  width=600px; height=400px;>");
    }
    else {
        out.println("Tuto stránku mohou vidět jen moderátoři");
    }

    %>
	
    
</body>
</html>