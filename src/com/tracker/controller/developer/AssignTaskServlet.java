package com.tracker.controller.developer;

import com.tracker.dao.EmployeeDAO;
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

public class AssignTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("view/developer/assignTask.jsp");
        HttpSession session = req.getSession();
        String employeeIdParam = req.getParameter("employeeId");

        if (employeeIdParam != null && !employeeIdParam.equals("self")) {
            try {
                Employee employee = EmployeeDAO.getEmployeeById(this, Integer.parseInt(employeeIdParam));

                req.setAttribute("employee", employee);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        session.setAttribute("assign-task-referer", req.getHeader("referer"));
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("view/developer/assignTask.jsp");
        HttpSession session = req.getSession();

        Employee employee = (Employee) session.getAttribute("employee");
        Employee assignedEmployee = null;

        String employeeIdParam = req.getParameter("employeeId");
        String titleParam = req.getParameter("title");
        String descriptionParam = req.getParameter("description");

        int assignedEmployeeId = -1;
        boolean authorized = false;

        if (employeeIdParam == null || titleParam == null || descriptionParam == null) {
            throw new ServletException("Invalid Request (Field/s missing)");
        }

        /* Check if the employee is authorized */
        if (employeeIdParam.equals("self") || employeeIdParam.isEmpty()) {
            assignedEmployeeId = employee.getId();
            employeeIdParam = "self";
            authorized = true;
        } else if (employee.getRole().getId() == 1) { /* Manager */
            assignedEmployeeId = Integer.parseInt(employeeIdParam);
            authorized = true;
        } else if (employee.getRole().getId() == 2) { /* Team Leader */
            assignedEmployeeId = Integer.parseInt(employeeIdParam);
            try {
                assignedEmployee = EmployeeDAO.getEmployeeById(this, assignedEmployeeId);

                if (assignedEmployee == null) {
                    throw new SQLException("Employee not found");
                }

                if (assignedEmployee.getTeam().getLeader().getId() == employee.getId()) {
                    authorized = true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (authorized) {
            if(employee.getRole().getId() == 1 && employeeIdParam.equals("self")) {
                req.setAttribute("error", "You can't assign task to yourself");
                req.setAttribute("employee", assignedEmployee);
                dispatcher.forward(req, resp);
                return;
            }

            TaskStatus status = employeeIdParam.equals("self") ? TaskStatus.PENDING:TaskStatus.TO_DO;
            Task newTask = new Task(-1, titleParam, descriptionParam, status);

            try {
                TaskDAO.addTask(this, newTask, assignedEmployeeId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            resp.sendRedirect((String) session.getAttribute("assign-task-referer"));
        } else {
            req.setAttribute("error", "You are not authorized to assign task to this employee");
            req.setAttribute("employee", assignedEmployee);
            dispatcher.forward(req, resp);
        }

    }
}
