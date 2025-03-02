<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 2/20/2025
  Time: 3:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<style>
    <%@include file="/assets/css/components/header.css" %>
</style>
<body style="margin: 0">
<div class="header">
    <a href="${pageContext.request.contextPath}/" class="logo">Tracker App</a>
    <div class="header-right">
        <a class="${requestScope.URI eq "/" ? 'active':''}" href="/">Home</a>
        <a class="${requestScope.URI eq "/profile" ? 'active':''}" href="${pageContext.request.contextPath}/profile">Profile</a>
        <c:choose>
            <c:when test="${sessionScope.employee.role.id eq 3 and not empty sessionScope.employee.team.name}">
                <a class="${requestScope.URI eq "/my-tasks" ? 'active':''}"
                   href="${pageContext.request.contextPath}/my-tasks">My Tasks</a>
            </c:when>
            <c:when test="${sessionScope.employee.role.id eq 2}">
                <a class="${requestScope.URI eq "/my-tasks" ? 'active':''}"
                   href="${pageContext.request.contextPath}/my-tasks">My Tasks</a>
                <a class="${requestScope.URI eq "/my-team" ? 'active':''}"
                   href="${pageContext.request.contextPath}/my-team">My Team</a>
                <a class="${requestScope.URI eq "/teams/tasks" ? 'active':''}"
                   href="${pageContext.request.contextPath}/teams/tasks?teamId=${sessionScope.employee.team.id}">Team Tasks</a>
                <a class="${requestScope.URI eq "/pending-tasks" ? 'active':''}"
                   href="${pageContext.request.contextPath}/pending-tasks">Pending Tasks</a>
            </c:when>
            <c:when test="${sessionScope.employee.role.id eq 1}">
                <a class="${requestScope.URI eq "/teams" ? 'active':''}"
                   href="${pageContext.request.contextPath}/teams">Teams</a>
                <a class="${requestScope.URI eq "/pending-tasks" ? 'active':''}"
                   href="${pageContext.request.contextPath}/pending-tasks">Pending Tasks</a>
                <a class="${requestScope.URI eq "/employees" ? 'active':''}"
                   href="${pageContext.request.contextPath}/employees">Employees</a>
            </c:when>
        </c:choose>
        <form method="post" action="${pageContext.request.contextPath}/logout" class="logout-form">
            <button class="logout">Logout</button>
        </form>
    </div>
</div>
</body>
</html>
