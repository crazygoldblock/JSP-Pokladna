<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page import="Core.Utils"%>

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

<!DOCTYPE html>
<html>
<head>
    <title>User Role Editor</title>
    <meta charset="UTF-8">
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

    <form action="editrole.jsp">
        <label>Email uživatele:</label><br>
        <input type="text" name="email"><br>
        <label>Role:</label><br>
        <select name="role">
            <option value="1">Čtenář</option>
            <option value="2">Moderátor</option>
            <option value="3">Administrátor</option>
        </select><br>
        <label>Akce:</label><br>
        <select name="akce">
            <option value="1">Přidat</option>
            <option value="2">Odstranit</option>
        </select><br><br>

        <button type="submit">Potvrdit</button>
    </form>
</body>
</html>