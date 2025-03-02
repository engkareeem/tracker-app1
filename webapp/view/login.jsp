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
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            margin: 0;
        }

        .text-input {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button {
            background-color: #04AA6D;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
            font-size: medium;
        }

        button:hover {
            opacity: 0.8;
        }

        .img-container {
            text-align: center;
            margin: 24px 0 12px 0;
            position: relative;
        }

        img.avatar {
            width: 200px;
            border-radius: 50%;
        }

        .container {
            padding: 16px;
        }

        .login-container {
            width: 500px;
        }

        .body-container {
            width: 100%;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .error-div {
            color: red;
        }
    </style>
</head>
<body>
<div class="body-container">
    <div class="login-container">
        <form method="post">
            <div class="img-container">
                <img src="https://media.discordapp.net/attachments/922243972875841586/1345516488890515476/default_avatar.jpg?ex=67c4d561&is=67c383e1&hm=d0d9b84d2a90aff3506022fc303e7a2be1fa0e84fdd60ebee7a67c1e8cb7595d&=&format=webp&width=504&height=504" alt="Avatar" class="avatar">
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
