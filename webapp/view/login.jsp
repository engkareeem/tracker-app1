<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 2/25/2025
  Time: 9:57 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
</head>
<body>
<div class="body-container">
    <div class="login-container">
        <form method="post">
            <div class="img-container">
                <img src="${pageContext.request.contextPath}/assets/default_avatar.jpg" alt="Avatar" class="avatar">
            </div>
            <div class="container">
                <label>
                    <b>Email</b>
                    <input type="text" class="text-input" placeholder="Enter Email" name="email" value="${param.email}" required>
                </label>

                <label>
                    <b>Password</b>
                    <input type="password" class="text-input" placeholder="Enter Password" name="password" value="${param.password}" required>
                </label>

                <div class="error-div">
                    ${requestScope.error}
                </div>

                <button type="submit">Login</button>
                <label>
                    <input type="checkbox" checked="checked" name="remember"> Remember me
                </label>
            </div>
        </form>
    </div>

</div>
</body>
</html>
