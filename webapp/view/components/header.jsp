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
    .header {
        overflow: hidden;
        background-color: #f1f1f1;
        padding: 10px 10px;
    }

    .header a {
        float: left;
        color: black;
        text-align: center;
        padding: 12px;
        text-decoration: none;
        font-size: 18px;
        line-height: 25px;
        border-radius: 4px;
    }

    .header .logout {
        float: left;
        text-align: center;
        padding: 12px;
        text-decoration: none;
        font-size: 18px;
        line-height: 25px;
        color: rgb(107, 107, 107);
        cursor: pointer;
        border: 0;
        border-left: 1px solid rgb(160, 160, 160);
        border-radius: 0;
    }

    .logout-form {
        all: unset;
    }

    .header a.logo {
        font-size: 25px;
        font-weight: bold;
    }

    .header a.active {
        background-color: #04AA6D;
        color: white;
    }

    .header-right {
        float: right;
    }
</style>
<body style="margin: 0">
<div class="header">
    <a href="${pageContext.request.contextPath}/" class="logo">Tracker App</a>
    <div class="header-right">
        <a class="${requestScope.URI eq "/" ? 'active':''}" href="/">Home</a>
        <c:choose>
            <c:when test="${sessionScope.employee.role.id eq 3}">
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
