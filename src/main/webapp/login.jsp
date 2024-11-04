<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div>
    <div>
        <h1><strong>Login</strong></h1>
    </div>
    <div>
        <form method="post" action="/user/login">
            <div>
                <label for="username">Username</label>
                <div>
                    <input name="username" type="text" id="username"
                           placeholder="Username">
                </div>
            </div>
            <div>
                <label for="password">Password</label>
                <div>
                    <input name="password" type="password" id="password"
                           placeholder="Password">
                </div>
            </div>
            <input type="submit" value="Login">
        </form>
    </div>
</div>
</body>
</html>
