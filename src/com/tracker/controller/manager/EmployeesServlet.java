package com.tracker.controller.manager;

import com.tracker.dao.EmployeeDAO;
import com.tracker.model.Employee;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/manager/employees.jsp");

        try {
            List<Employee> employees = EmployeeDAO.getEmployees(this, -1);
            req.setAttribute("employees", employees);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dispatcher.forward(req, resp);
    }
}
