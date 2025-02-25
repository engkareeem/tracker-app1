<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP Page</title>
</head>
<body>
<h2>Welcome ${sessionScope.employee.name}!</h2>
<h4>Your role is ${sessionScope.employee.role.name}</h4>
<a href="hello">Go to Servlet</a>
<a href="${pageContext.request.contextPath}/db">Go to db</a>
<form method="post" action="${pageContext.request.contextPath}/logout"><button type="submit">Logout</button></form>
</body>
</html>
