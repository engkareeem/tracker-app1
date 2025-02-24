package com.tracker;

import com.tracker.dao.EmployeeDAO;
import com.tracker.model.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if(idParam == null) {
            resp.getWriter().println("<h1>invalid request</h1>");
            return;
        }
        int id = Integer.parseInt(idParam);
        try {
            Employee employee = EmployeeDAO.getEmployeeById(this, id);
            if(employee == null) throw new SQLException("Employee not found");
            resp.setContentType("text/html");
            resp.getWriter().println("<h1>Update Employee "+ id + "</h1>");
            resp.getWriter().println("<form method='post'>");
            resp.getWriter().println("<label for='name'>Name</label>");
            resp.getWriter().println("<input type='text' value='" + employee.getName() + "' id='name' name='name' required>");
            resp.getWriter().println("<label for='contact'>Contact</label>");
            resp.getWriter().println("<input type='text' value='" + employee.getEmail() + "' id='contact' name='contact' required>");
            resp.getWriter().println("<label for='role'>Role " + employee.getRole().getId() + " </label>");
            resp.getWriter().println("<select id='role' name='role'>");
            resp.getWriter().println("<option value='3' "+ ((employee.getRole().getId() == 3) ? "selected" : "")+">Developer</option>");
            resp.getWriter().println("<option value='2' "+ ((employee.getRole().getId() == 2) ? "selected" : "")+">Team Leader</option>");
            resp.getWriter().println("<option value='1' "+ ((employee.getRole().getId() == 1) ? "selected" : "")+">Manager</option>");
            resp.getWriter().println("</select>");
            resp.getWriter().println("<label for='team'>Team</label>");
            resp.getWriter().println("<select id='team' name='team'>");
            resp.getWriter().println("<option value='1' "+ ((employee.getTeam().getId() == 1) ? "selected" : "")+">Copper</option>");
            resp.getWriter().println("<option value='2' "+ ((employee.getTeam().getId() == 2) ? "selected" : "")+">Testing</option>");
            resp.getWriter().println("<option value='3' "+ ((employee.getTeam().getId() == 3) ? "selected" : "")+">Obsidian</option>");
            resp.getWriter().println("</select>");
            resp.getWriter().println("<button type='submit'>Update</button>");
            resp.getWriter().println("</form>");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
