<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 2/26/2025
  Time: 4:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Assign Task</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/developer/assignTask.css">
</head>
<body>
<jsp:include page="/view/components/header.jsp"/>
<div class="body-container">
    <div class="assign-container">
        <form method="post">
            <input type="hidden" name="employeeId" value="${param.employeeId}">
            <div class="container">
                <c:choose>
                    <c:when test="${requestScope.employee ne null}">
                        <div>
                            <h1>Assign Task for ${requestScope.employee.name}</h1>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <h1>Assign Task</h1>
                        </div>
                    </c:otherwise>
                </c:choose>

                <label>
                    <b>Title</b>
                    <input type="text" class="text-input" placeholder="Enter Title" name="title" value="${param.title}" required>
                </label>

                <label>
                    <b>Description</b>
                    <input type="text" class="text-input" placeholder="Enter Description" name="description"
                           value="${param.description}" required>
                </label>

                <div class="error-div">
                    ${requestScope.error}
                </div>

                <button type="submit">Assign</button>
            </div>
        </form>
    </div>

</div>
<jsp:include page="/view/components/footer.jsp"/>

</body>
</html>
