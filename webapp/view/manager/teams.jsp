<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 2/27/2025
  Time: 3:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Teams</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/manager/teams.css">
</head>
<body>
<jsp:include page="/view/components/header.jsp"/>

<div class="body-container">
    <h2>Teams</h2>
    <form method="get">
        <div class="radio-container">
            <b>Sort By: </b>
            <div class="by-container">
                <div>
                    <input type="radio" id="id" name="sort"
                           value="id"  ${param.sort eq "id" or param.sort eq null ? "checked":""}/>
                    <label for="id">ID</label>
                </div>

                <div>
                    <input type="radio" id="name" name="sort" value="name" ${param.sort eq "name" ? "checked":""}/>
                    <label for="name">Name</label>
                </div>

                <div>
                    <input type="radio" id="leader" name="sort"
                           value="leader" ${param.sort eq "leader" ? "checked":""}/>
                    <label for="leader">Leader</label>
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
    <div class="new-button-container">
        <form method="post">
            <button type="submit" class="new-button">New Team</button>
        </form>
    </div>
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Leader ID</th>
            <th>Leader Name</th>
            <th>Leader Email</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="team" items="${requestScope.teams}">
            <tr>
                <td>${team.id}</td>
                <td>${team.name}</td>
                <td>${team.leader.id}</td>
                <td>${team.leader.name}</td>
                <td>${team.leader.email}</td>
                <td>
                    <div class="actions-container">
                        <form method="get">
                            <input type="hidden" value="${team.id}" name="teamId">
                            <button type="submit">
                                View Team
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
