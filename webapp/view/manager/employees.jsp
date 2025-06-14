<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 2/27/2025
  Time: 4:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>My Team</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/manager/employees.css">
</head>
<body>
<jsp:include page="/view/components/header.jsp"/>
<div class="body-container">
    <h2>Employees</h2>
    <form method="get">
        <input type="hidden" name="teamId" value="${requestScope.team.id}">
        <div class="search-container">
            <label for="search"><b>Search: </b></label>
            <input type="text" id="search" name="search" value="${param.search}">
            <button type="submit">Search</button>
        </div>
        <div class="search-container">
            <label for="employeeId"><b>By EmployeeId: </b></label>
            <input type="number" id="employeeId" name="employeeId" value="${param.employeeId}">
            <button type="submit">Search</button>
        </div>
        <div class="radio-container">
            <b>Sort By: </b>
            <div class="by-container">
                <div>
                    <input type="radio" id="id" name="sort"
                           value="id"  ${param.sort eq "id" or param.sort eq null ? "checked":""}/>
                    <label for="id">ID</label>
                </div>

                <div>
                    <input type="radio" id="name" name="sort"
                           value="name" ${param.sort eq "name" ? "checked":""}/>
                    <label for="name">Name</label>
                </div>
            </div>

            <div class="order-container">
                <div>
                    <input type="radio" id="asc" name="order"
                           value="asc" ${param.order eq "asc" or param.order eq null? "checked":""} />
                    <label for="asc">Asc</label>
                </div>

                <div>
                    <input type="radio" id="desc" name="order" value="desc" ${param.order eq "desc" ? "checked":""} />
                    <label for="desc">Desc</label>
                </div>
            </div>

            <button type="submit">Sort</button>
        </div>
    </form>
    <form method="post">
        <div class="table-actions-container">
            <button type="submit" class="save-button">Save</button>

            <a href="${pageContext.request.contextPath}/employees/new">
                <button type="button">
                    Add Employee
                </button>
            </a>
        </div>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Team</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="employee" items="${requestScope.employees}">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.name}</td>
                    <td>${employee.email}</td>
                    <td>
                        <label for="${employee.id}-role-${employee.role.id}"></label>
                        <select name="${employee.id}-role-${employee.role.id}" id="${employee.id}-role-${employee.role.id}"
                                title="Employee Role" ${employee.role.id eq 1 ? "disabled":""}>

                            <option value="3" ${employee.role.id eq 3 ? "selected":""}>Developer</option>
                            <option value="2" ${employee.role.id eq 2 ? "selected":""}>Team Leader</option>
                            <option value="1" ${employee.role.id eq 1 ? "selected":""}>Manager</option>
                        </select>
                    </td>
                    <td>
                        <label for="${employee.id}-team-${empty employee.team.name ? "none":employee.team.id}"></label>
                        <select name="${employee.id}-team-${empty employee.team.name ? "none":employee.team.id}"
                                id="${employee.id}-team-${empty employee.team.name ? "none":employee.team.id}"
                                title="Employee Team" ${employee.role.id != 3 ? "disabled":""}>

                            <option value="none" ${empty employee.team ? "selected":""}>None</option>
                            <c:forEach var="team" items="${requestScope.teams}">
                                <option value="${team.id}" ${employee.team.id eq team.id ? "selected":""}>${team.id}-${team.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <div class="actions-container">
                            <c:if test="${not empty employee.team.name}">
                                <a href="${pageContext.request.contextPath}/teams/tasks?teamId=${employee.team.id}&employeeId=${employee.id}">
                                    <button type="button">
                                        View Tasks
                                    </button>
                                </a>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</div>
<jsp:include page="/view/components/footer.jsp"/>
</body>
</html>
