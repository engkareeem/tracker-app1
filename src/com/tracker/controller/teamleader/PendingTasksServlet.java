package com.tracker.controller.teamleader;

import com.tracker.dao.TaskDAO;
import com.tracker.model.Employee;
import com.tracker.model.Task;
import com.tracker.model.TaskStatus;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PendingTasksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/teamleader/pendingTasks.jsp");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        if (employee == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            List<Task> pendingTasks;

            if(employee.getRole().getId() == 2 && employee.getTeam() != null) { /* Team leader */
                pendingTasks = TaskDAO.getPendingTasks(this, employee);
            } else if(employee.getRole().getId() == 1) { /* Manager */
                pendingTasks = TaskDAO.getPendingTasks(this, null);
            } else {
                resp.sendRedirect("/");
                return;
            }

            req.setAttribute("tasks", pendingTasks);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String approvalParam = req.getParameter("approval");
        String taskIdParam = req.getParameter("taskId");

        if(approvalParam != null && taskIdParam != null) {
            if(approvalParam.equals("approve")) {
                Task task = new Task(Integer.parseInt(taskIdParam));

                task.setStatus(TaskStatus.TO_DO);

                try {
                    TaskDAO.updateTask(this, task);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if(approvalParam.equals("reject")) {
                int taskId = Integer.parseInt(taskIdParam);

                try {
                    TaskDAO.removeTask(this, taskId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        resp.sendRedirect(req.getHeader("Referer"));
    }
}
