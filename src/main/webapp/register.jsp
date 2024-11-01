<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<div>
    <div>
        <h1><strong>Register</strong></h1>
        <p>${error}</p>
    </div>
    <div>
        <form method="post" action="/user/register">
            <div>
                <label for="username">Username</label>
                <div>
                    <input name="username" type="text" id="username"
                           placeholder="Username">
                </div>
            </div>
            <div>
                <label for="first-name">First Name</label>
                <div>
                    <input name="first-name" type="text" id="first-name"
                           placeholder="First Name">
                </div>
            </div>
            <div>
                <label for="last-name">Last Name</label>
                <div>
                    <input name="last-name" type="text" id="last-name"
                           placeholder="Last Name">
                </div>
            </div>
            <div>
                <label for="email">Username</label>
                <div>
                    <input name="email" type="text" id="email"
                           placeholder="Email">
                </div>
            </div>
            <div>
                <label for="password">Password</label>
                <div>
                    <input name="password" type="password" id="password"
                           placeholder="Password">
                </div>
            </div>
            <div>
                <label for="rep-password">Password</label>
                <div>
                    <input name="rep-password" type="password" id="rep-password"
                           placeholder="Repeat Password">
                </div>
            </div>
            <input type="submit" value="Register">
        </form>
    </div>
</div>
</body>
</html>