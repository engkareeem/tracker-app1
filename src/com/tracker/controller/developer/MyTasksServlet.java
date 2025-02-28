package com.tracker.controller.developer;

import com.tracker.dao.TaskDAO;
import com.tracker.model.Employee;
import com.tracker.model.Task;
import com.tracker.model.TaskStatus;
import com.tracker.util.Utils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class MyTasksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("view/developer/myTasks.jsp");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        String sortParam = req.getParameter("sort");
        String orderParam = req.getParameter("order");

        if(sortParam == null) {
            sortParam = "id";
        }

        if(orderParam == null) {
            orderParam = "asc";
        }

        if (employee != null) {
            List<Task> tasks = null;

            try {
                tasks = TaskDAO.getEmployeeTasks(this, employee.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            tasks = Utils.sortTasks(tasks, sortParam, orderParam);

            req.setAttribute("tasks", tasks);
        } else {
            req.setAttribute("tasks", new ArrayList<Task>()); /* Empty */
        }

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration<String> keys = req.getParameterNames();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = req.getParameter(key);
            String[] keyParts = key.split("-");

            if(keyParts.length != 3) continue;

            String taskId = keyParts[0];
            String originalStatus = keyParts[2];

            if (value.equals(originalStatus) || originalStatus.equals("0")) continue;

            Task task = new Task(Integer.parseInt(taskId));

            task.setStatus(TaskStatus.fromValue(Integer.parseInt(value)));

            try {
                TaskDAO.updateTask(this, task);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        resp.sendRedirect(req.getHeader("referer"));
    }
}
