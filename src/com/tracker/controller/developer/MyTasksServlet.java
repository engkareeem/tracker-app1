package com.tracker.controller.developer;

import com.tracker.model.Employee;
import com.tracker.model.Task;
import com.tracker.util.Utils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyTasksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("view/developer/myTasks.jsp");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        String sortParam = req.getParameter("sort");
        String orderParam = req.getParameter("order");

        if (employee != null) {
            List<Task> tasks = employee.getTasks();
            if (sortParam != null && orderParam != null) {
                int by = 0;
                boolean order = true;

                if (sortParam.equals("status")) {
                    by = 1;
                }

                if (orderParam.equals("desc")) {
                    order = false;
                }

                tasks = Utils.sortTasks(employee.getTasks(), by, order);
            }
            req.setAttribute("tasks", tasks);
        } else {
            req.setAttribute("tasks", new ArrayList<Task>()); /* Empty */
        }

        dispatcher.forward(req, resp);
    }
}
