<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<header>
<form method="post" action="/user/logout">
    <input type="submit" value="Logout">
</form>
</header>
<h1>${user.username}</h1>
<h1>${user}</h1>
<h1>Last Name</h1>
<h1>Email</h1>
<form action="/user/edit">
    <input type="submit" value="Edit">
</form>
<form method="post" action="/user/logout">
    <input type="submit" value="Logout">
</form>
</body>
</html>
