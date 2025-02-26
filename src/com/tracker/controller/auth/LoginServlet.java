package com.tracker.controller.auth;

import com.tracker.dao.AuthDAO;
import com.tracker.model.Employee;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("view/login.jsp");

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("view/login.jsp");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if(email != null && password != null) {
            try {
                Employee employee = AuthDAO.login(this, email, password);
                if(employee != null) {
                    HttpSession session = req.getSession();
                    session.setAttribute("employee", employee);
                    resp.sendRedirect("/");
                } else {
                    req.setAttribute("error", "Invalid email or password");
                    dispatcher.forward(req, resp);

                }
            } catch (SQLException e) {
                req.setAttribute("error", e.getMessage());
                dispatcher.forward(req, resp);
            }
        }
    }
}
