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

<!DOCTYPE html>
<html>
<head>
    <title>User Table</title>
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


    <table>
        <thead>
            <tr>
                <th>Email</th>
                <th>Role</th>
            </tr>
        </thead>
        <tbody>

        <%@page import="Core.Role"%>
        <%@page import="Core.User"%>

        <% 

        User[] uzivatele = Utils.GetUzivatele();

        for (int i = 0; i < uzivatele.length; i++) { 
            out.println("<tr><td>" + uzivatele[i].email + "</td>");

            for (int j = 0; j < uzivatele[i].role.length; j++) { 
                out.println("<td>" + uzivatele[i].role[j].toString() + "</td>");
            }

            out.println("</tr>");
        }
        
        %>

        

        </tbody>
    </table>

    <a href="editroles.jsp">
        <button>Upravit Role</button>
    </a>
</body>
</html>