<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 2/26/2025
  Time: 10:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<style>

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

    .save-button {

    }

    .save-button-container {
        width: 100%;
        display: flex;
        justify-content: end;
    }


</style>
<body>
<form method="post">
    <div class="save-button-container">
        <button type="submit" class="save-button">Save</button>
    </div>
    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Status</th>
        </tr>
        <c:forEach var="task" items="${requestScope.tasks}">
            <tr>
                <td>${task.id}</td>
                <td>${task.title}</td>
                <td>${task.description}</td>
                <td>
                    <label for="${task.id}-status-${task.status.value}"></label>
                    <select name="${task.id}-status-${task.status.value}" id="${task.id}-status-${task.status.value}"
                            title="Task Status" ${task.status.value eq 0 ? "disabled":""}>
                        <c:if test="${task.status.value eq 0}">
                            <option value="0" selected>Pending</option>
                        </c:if>
                        <option value="1" ${task.status.value eq 1 ? "selected":""}>To Do</option>
                        <option value="2" ${task.status.value eq 2 ? "selected":""}>In Progress</option>
                        <option value="3" ${task.status.value eq 3 ? "selected":""}>Completed</option>
                    </select>
                </td>


            </tr>
        </c:forEach>
    </table>

</form>
</body>
</html>
