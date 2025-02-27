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

        .new-button-container {
            width: 100%;
            display: flex;
            justify-content: end;
        }
    </style>
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
