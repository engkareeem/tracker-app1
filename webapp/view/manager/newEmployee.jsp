<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 2/27/2025
  Time: 2:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>New Employee</title>
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
                <img src="${pageContext.request.contextPath}/assets/default_avatar.jpg" alt="Avatar" class="avatar">
            </div>
            <div class="container">
                <label>
                    <b>Name</b>
                    <input type="text" class="text-input" placeholder="Enter Name" name="name" value="${param.name}" required>
                </label>

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

                <button type="submit">Create</button>
            </div>
        </form>
    </div>

</div>
</body>
</html>