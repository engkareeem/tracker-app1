<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 2/27/2025
  Time: 3:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>New Team</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/manager/newTeam.css">
</head>
<body>
<jsp:include page="/view/components/header.jsp"/>
<div class="body-container">
    <div class="assign-container">
        <form method="post">
            <input type="hidden" name="employeeId" value="${param.employeeId}">
            <div class="container">
                <div>
                    <h1>New Team</h1>
                </div>

                <label>
                    <b>Name</b>
                    <input type="text" class="text-input" placeholder="Enter Name" name="name" required>
                </label>

                <label for="team-leader">Choose Leader: </label>
                <select name="leaderId" id="team-leader" title="Team Leader" required>
                    <c:if test="${empty requestScope.leaders}">
                        <option value="">No Leaders Available</option>
                    </c:if>
                    <c:forEach var="leader" items="${requestScope.leaders}">
                        <option value="${leader.id}">${leader.id} - ${leader.name}</option>
                    </c:forEach>
                </select>

                <div class="error-div">
                    ${requestScope.error}
                </div>

                <button type="submit" ${empty requestScope.leaders ? "disabled":""}>Create</button>
            </div>
        </form>
    </div>

</div>
<jsp:include page="/view/components/footer.jsp"/>
</body>
</html>
