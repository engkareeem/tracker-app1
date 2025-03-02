package com.tracker.controller.developer;

import com.tracker.dao.AuthDAO;
import com.tracker.dao.EmployeeDAO;
import com.tracker.model.Employee;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("view/developer/profile.jsp");

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("view/developer/editProfile.jsp");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        String newParam = req.getParameter("new");

        if(newParam != null) {
            dispatcher.forward(req, resp);
        } else {
            String nameParam = req.getParameter("name");
            String emailParam = req.getParameter("email");
            String oldPasswordParam = req.getParameter("oldPassword");
            String passwordParam = req.getParameter("password");

            if(nameParam == null || emailParam == null || nameParam.isEmpty() || emailParam.isEmpty()) {
                req.setAttribute("error", "Name and Email cannot be empty");
            } else {
                Employee updatedEmployee = new Employee(employee.getId(), nameParam, emailParam);

                try {
                    if(oldPasswordParam != null && passwordParam != null &&
                            !oldPasswordParam.isEmpty() && !passwordParam.isEmpty()) {
                        AuthDAO.updatePassword(this, employee.getId(), oldPasswordParam, passwordParam);
                    } else {
                        req.removeAttribute("oldPassword");
                        req.removeAttribute("password");
                    }

                    EmployeeDAO.updateEmployee(this,updatedEmployee);
                    employee.setName(nameParam);
                    employee.setEmail(emailParam);
                    resp.sendRedirect("/profile");
                } catch (SQLException e) {
                    req.setAttribute("error", e.getMessage());
                    dispatcher.forward(req, resp);
                }
            }
        }
    }
}
