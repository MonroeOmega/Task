<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<header>
    <form method="post" action="/user/logout">
        <input type="submit" value="Logout">
    </form>
</header>
<div>
    <div>
        <h1><strong>Edit</strong></h1>
    </div>
    <div>
        <form method="post" action="/user/edit">
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
                <label for="email">Email</label>
                <div>
                    <input name="email" type="text" id="email"
                           placeholder="Email">
                    <small>Email must be valid.</small>
                </div>
            </div>
            <div>
                <label for="password">Password</label>
                <div>
                    <input name="password" type="password" id="password"
                           placeholder="Password">
                    <small>Password must be 5 to 20 symbols.</small>
                </div>
            </div>
            <div>
                <label for="rep-password">Password</label>
                <div>
                    <input name="rep-password" type="password" id="rep-password"
                           placeholder="Repeat Password">
                    <small>Password must be 5 to 20 symbols.</small>
                </div>
            </div>
            <input type="submit" value="Edit">
        </form>
    </div>
</div>
</body>
</html>
