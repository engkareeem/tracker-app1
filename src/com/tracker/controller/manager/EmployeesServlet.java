package com.tracker.controller.manager;

import com.tracker.dao.EmployeeDAO;
import com.tracker.dao.TaskDAO;
import com.tracker.model.Employee;
import com.tracker.model.Role;
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
import java.util.Enumeration;
import java.util.List;

public class EmployeesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/manager/employees.jsp");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        if(employee != null && employee.getRole().getId() == 1) {
            try {
                List<Employee> employees = EmployeeDAO.getEmployees(this, -1);

                req.setAttribute("employees", employees);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            dispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(req.getHeader("/"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration<String> keys = req.getParameterNames();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = req.getParameter(key);
            String[] keyParts = key.split("-");

            if(keyParts.length != 3) continue;
            String employeeId = keyParts[0];
            String originalRole = keyParts[2];

            if (value.equals(originalRole)) continue;

            Employee newEmployee = new Employee(Integer.parseInt(employeeId));

            newEmployee.setRole(new Role(Integer.parseInt(value)));

            try {
                EmployeeDAO.updateEmployee(this, newEmployee);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        resp.sendRedirect(req.getHeader("referer"));
    }
}
