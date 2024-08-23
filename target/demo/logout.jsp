<%

session.removeAttribute("email");
session.removeAttribute("heslo");

response.sendRedirect("login.html");

%>