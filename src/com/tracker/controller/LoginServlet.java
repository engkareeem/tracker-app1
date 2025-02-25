package com.tracker.controller;

import com.tracker.dao.AuthDAO;
import com.tracker.model.Employee;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("Login.jsp");

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("Login.jsp");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if(email != null && password != null) {
            try {
                Employee employee = AuthDAO.login(this, email, password);
                if(employee != null) {
                    req.setAttribute("error", employee.getName());
                } else {
                    req.setAttribute("error", "Invalid email or password");

                }
            } catch (SQLException e) {
                req.setAttribute("error", e.getMessage());
            }
        }
        dispatcher.forward(req, resp);
    }
}
