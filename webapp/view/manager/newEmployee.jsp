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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/manager/newEmployee.css">
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