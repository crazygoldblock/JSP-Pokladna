<%@page import="Core.Login"%>
<%@page import="Core.Role"%>

<html>  
<body>  
<%  
String email = request.getParameter("email");  
String heslo = request.getParameter("heslo");

Login.AddNewUser("pomoc", "hello there");
Login.AddNewUser("ja", "nezvladam");
Login.AddNewUser("to", "harry potter");

//boolean status = Login.ValidateLogin("pomoc", "hello there");

boolean status = Login.CheckEmail("ja");

out.print(Role.Ctenar.hashCode());

if (status) {
    out.print("welcome " + email + " " + heslo);  
}
else {
    out.print("nn lmao");
}

%>  
</form>  
</body>  
</html>  