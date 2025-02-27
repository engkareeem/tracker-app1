<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 2/26/2025
  Time: 3:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Team Tasks</title>
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

        .search-container {
            display: flex;
            gap: 10px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="/view/components/header.jsp"/>
<div class="body-container">
    <h2>${requestScope.team.name} Tasks</h2>
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
                    <input type="radio" id="status" name="sort"
                           value="status" ${param.sort eq "status" ? "checked":""}/>
                    <label for="status">Status</label>
                </div>

                <div>
                    <input type="radio" id="employee" name="sort"
                           value="employee" ${param.sort eq "employee" ? "checked":""}/>
                    <label for="employee">Employee</label>
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
    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Status</th>
            <th>Employee ID</th>
            <th>Employee Name</th>
        </tr>
        <c:forEach var="task" items="${requestScope.tasks}">
            <tr>
                <td>${task.id}</td>
                <td>${task.title}</td>
                <td>${task.description}</td>
                <td>${task.status.getLabel()}</td>
                <td>${task.assignedTo.id}</td>
                <td>${task.assignedTo.name}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="/view/components/footer.jsp"/>
</body>
</html>
