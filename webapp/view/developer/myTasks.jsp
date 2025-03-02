<%--
  Created by IntelliJ IDEA.
  User: kareem
  Date: 2/26/2025
  Time: 8:39 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
  <title>My Tasks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/developer/myTasks.css">
</head>
<body>
<jsp:include page="/view/components/header.jsp"/>
<div class="body-container">
  <h2>My Tasks</h2>
  <form method="get">
    <div class="radio-container">
      <b>Sort By: </b>
      <div class="by-container">
        <div>
          <input type="radio" id="id" name="sort" value="id"  ${param.sort eq "id" or param.sort eq null ? "checked":""}/>
          <label for="id">ID</label>
        </div>

        <div>
          <input type="radio" id="status" name="sort" value="status" ${param.sort eq "status" ? "checked":""}/>
          <label for="status">Status</label>
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
    <div class="save-button-container">
      <button type="submit" class="save-button">Save</button>
      <a href="${pageContext.request.contextPath}/assign-task?employeeId=self" style="margin-left: 10px">
        <button type="button">New Task</button>
      </a>
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
</div>
<jsp:include page="/view/components/footer.jsp"/>

</body>
</html>

