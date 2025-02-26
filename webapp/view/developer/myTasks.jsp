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
  <style>
    .body-container {
      padding-left: 10px;
      padding-right: 10px;
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
  </style>
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
  <jsp:include page="/view/components/tasksTable.jsp"/>
</div>
<jsp:include page="/view/components/footer.jsp"/>

</body>
</html>

