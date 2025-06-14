<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 2/27/2025
  Time: 1:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Pending Tasks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/teamleader/pendingTasks.css">
</head>
<body>
<jsp:include page="/view/components/header.jsp"/>
<div class="body-container">
    <h2>Pending Tasks</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Employee ID</th>
            <th>Employee Name</th>
            <th>Employee Description</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="task" items="${requestScope.tasks}">
            <tr>
                <td>${task.id}</td>
                <td>${task.title}</td>
                <td>${task.description}</td>
                <td>${task.assignedTo.id}</td>
                <td>${task.assignedTo.name}</td>
                <td>${task.assignedTo.email}</td>
                <td>
                    <div class="actions-container">
                        <form method="post">
                            <input type="hidden" name="taskId" value="${task.id}">
                            <input type="hidden" name="approval" value="approve">
                            <button>
                                Approve
                            </button>
                        </form>
                        <form method="post">
                            <input type="hidden" name="taskId" value="${task.id}">
                            <input type="hidden" name="approval" value="reject">
                            <button>
                                Reject
                            </button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="/view/components/footer.jsp"/>

</body>
</html>
