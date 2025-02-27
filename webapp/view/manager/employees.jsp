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
    <style>
        .body-container {
            padding-left: 10px;
            padding-right: 10px;
        }

        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
            margin-top: 10px;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }

        .actions-container {
            display: flex;
            gap: 10px;
        }

        .actions-container a {
            cursor: pointer;
        }

        .radio-container {
            display: flex;
        }

        .by-container, .order-container {
            display: flex;
            padding-right: 10px;
            padding-left: 5px;
        }
        .by-container {
            border-right: 1px solid rgb(162, 162, 162);
        }

        .table-actions-container {
            display: flex;
            justify-content: flex-end;
            width: 100%;
            gap: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="/view/components/header.jsp"/>
<div class="body-container">
    <h2>Employees</h2>
    <form method="get">
        <div class="radio-container">
            <b>Sort By: </b>
            <div class="by-container">
                <div>
                    <input type="radio" id="id" name="sort" value="id"  ${param.sort eq "id" or param.sort eq null ? "checked":""}/>
                    <label for="id">ID</label>
                </div>

                <div>
                    <input type="radio" id="name" name="sort" value="name" ${param.sort eq "name" ? "checked":""}/>
                    <label for="name">Name</label>
                </div>
            </div>

            <div class="order-container">
                <div>
                    <input type="radio" id="asc" name="order" value="asc" ${param.order eq "asc" or param.order eq null? "checked":""} />
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
                <th>Actions</th>
            </tr>
            <c:forEach var="employee" items="${requestScope.employees}">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.name}</td>
                    <td>${employee.email}</td>
                    <td>
                        <label for="${employee.id}-employee-${employee.role.id}"></label>
                        <select name="${employee.id}-employee-${employee.role.id}" id="${employee.id}-employee-${employee.role.id}"
                                title="Employee Role" ${employee.role.id eq 1 ? "disabled":""}>

                            <option value="3" ${employee.role.id eq 3 ? "selected":""}>Developer</option>
                            <option value="2" ${employee.role.id eq 2 ? "selected":""}>Team Leader</option>
                            <option value="1" ${employee.role.id eq 1 ? "selected":""}>Manager</option>
                        </select>
                    </td>
                    <td>
                        <div class="actions-container">
                            <c:if test="${not empty employee.team}">
                                <a href="${pageContext.request.contextPath}/teams/tasks?teamId=${employee.team.id}&employeeId=${employee.id}">
                                    <button>
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
