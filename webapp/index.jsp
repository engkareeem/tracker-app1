<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Tracker App</title>
    <style>
        .body-container {
            padding-left: 10px;
            padding-right: 10px;
        }
    </style>
</head>
<body style="margin: 0">
<jsp:include page="view/components/header.jsp"/>

<div class="body-container">
    <h2>Welcome ${sessionScope.employee.name}!</h2>
    <h4>You are a ${sessionScope.employee.role.name} in this app!</h4>
    <form method="post" action="${pageContext.request.contextPath}/logout">
        <button type="submit">Logout</button>
    </form>
</div>

<jsp:include page="view/components/footer.jsp"/>
</body>
</html>
