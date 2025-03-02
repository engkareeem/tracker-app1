<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 3/2/2025
  Time: 10:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/developer/profile.css">
</head>
<body>
<jsp:include page="/view/components/header.jsp"/>

<div class="body-container">
    <div class="profile-container">
        <img src="${pageContext.request.contextPath}/assets/default_avatar.jpg" alt="Avatar">
        <h2>${sessionScope.employee.name}</h2>
        <p>${sessionScope.employee.email}</p>
        <p>Role: ${sessionScope.employee.role.name}</p>
        <form method="post">
            <input type="hidden" name="new" value="true">
            <button type="submit">Edit profile</button>
        </form>
    </div>
</div>

</body>
</html>