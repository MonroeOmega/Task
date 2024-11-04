<%@ page import="app.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%User user = (User) request.getAttribute("user"); %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<header>
    <form method="post" action="/user/logout">
        <input type="submit" value="Logout">
    </form>
    <h1>Profile</h1>
</header>
<h3>Username: <%= user.getUsername() %>
</h3>
<h3>Human name: <%= user.getFirstName()%> <%= user.getLastName()%>
</h3>
<h3>Email: <%= user.getEmail()%>
</h3>
<form action="/user/edit">
    <input type="submit" value="Edit">
</form>
<form method="post" action="/user/logout">
    <input type="submit" value="Logout">
</form>
</body>
</html>
