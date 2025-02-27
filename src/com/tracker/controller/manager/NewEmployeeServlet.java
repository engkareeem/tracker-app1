package com.tracker.controller.manager;

import com.tracker.dao.AuthDAO;
import com.tracker.model.Employee;
import com.tracker.model.Role;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

public class NewEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/manager/newEmployee.jsp");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        if(employee == null || employee.getRole().getId() != 1) {
            resp.sendRedirect("/");
            return;
        }

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameParam = req.getParameter("name");
        String emailParam = req.getParameter("email");
        String passwordParam = req.getParameter("password");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        if(employee == null || employee.getRole().getId() != 1) {
            resp.sendRedirect("/");
            return;
        }

        if(nameParam != null && emailParam != null && passwordParam != null) {
            Employee newEmployee = new Employee(-1, nameParam, emailParam);

            newEmployee.setRole(new Role(3));
            try {
                AuthDAO.createAccount(this, newEmployee, passwordParam);
                resp.sendRedirect("/employees");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            resp.sendRedirect(req.getHeader("referer"));
        }
    }
}
