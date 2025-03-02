<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 3/2/2025
  Time: 11:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/developer/editProfile.css">
</head>
<body>
<jsp:include page="/view/components/header.jsp"/>
<div class="body-container">
    <div class="login-container">
        <form method="post">
            <div class="img-container">
                <img src="${pageContext.request.contextPath}/assets/default_avatar.jpg" alt="Avatar" class="avatar">
            </div>
            <div class="container">
                <label>
                    <b>Name</b>
                    <input type="text"
                           class="text-input"
                           placeholder="Enter Name"
                           name="name"
                           value="${not empty param.name ? param.name : sessionScope.employee.name}"
                           required>
                </label>

                <label>
                    <b>Email</b>
                    <input type="text"
                           class="text-input"
                           placeholder="Enter Email"
                           name="email"
                           value="${not empty param.email ? param.email : sessionScope.employee.email}"
                           required>
                </label>

                <div class="password-container">
                    <label>
                        <b>Old Password</b>
                        <input type="password" class="text-input" placeholder="Enter Password" name="oldPassword" value="${param.oldPassword}">
                    </label>
                    <label>
                        <b>New Password</b>
                        <input type="password" class="text-input" placeholder="Enter Password" name="password" value="${param.newPassword}">
                    </label>
                </div>

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
