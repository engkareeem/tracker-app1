package com.tracker;

import com.tracker.dao.EmployeeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RemoveEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        res.getWriter().println("<h1>Remove Employee</h1>");
        res.getWriter().println("<form method='post'>");
        res.getWriter().println("<label for='id'>ID</label>");
        res.getWriter().println("<input type='number' id='id' name='id' required>");
        res.getWriter().println("<button type='submit'>Remove</button>");
        res.getWriter().println("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        EmployeeDAO.removeEmployeeById(this, id);

        res.sendRedirect(req.getHeader("referer"));
    }
}
