package com.tracker;

import com.tracker.dao.EmployeeDAO;
import com.tracker.model.Employee;
import com.tracker.model.Role;
import com.tracker.model.Team;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class AddEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.getWriter().println("<h1>Add Employee</h1>");
        resp.getWriter().println("<form method='post'>");
        resp.getWriter().println("<label for='name'>Name</label>");
        resp.getWriter().println("<input type='text' id='name' name='name' required>");
        resp.getWriter().println("<label for='contact'>Contact</label>");
        resp.getWriter().println("<input type='text' id='contact' name='contact' required>");
        resp.getWriter().println("<label for='role'>Role</label>");
        resp.getWriter().println("<select id='role' name='role'>");
        resp.getWriter().println("<option value='3'>Developer</option>");
        resp.getWriter().println("<option value='2'>Team Leader</option>");
        resp.getWriter().println("<option value='1'>Manager</option>");
        resp.getWriter().println("</select>");
        resp.getWriter().println("<label for='team'>Team</label>");
        resp.getWriter().println("<select id='team' name='team'>");
        resp.getWriter().println("<option value='1'>Copper</option>");
        resp.getWriter().println("<option value='2'>Testing</option>");
        resp.getWriter().println("<option value='3'>Management</option>");
        resp.getWriter().println("</select>");
        resp.getWriter().println("<button type='submit'>Add</button>");
        resp.getWriter().println("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("name");
        String contact = req.getParameter("contact");
        int role = Integer.parseInt(req.getParameter("role"));
        int team = Integer.parseInt(req.getParameter("team"));

        Employee employee = new Employee();
        employee.setName(name);
        employee.setContact(contact);
        employee.setRole(new Role(role));
        employee.setTeam(new Team(team));

        try {
            EmployeeDAO.addEmployee(this, employee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        res.sendRedirect(req.getHeader("referer"));
        // Add employee to database
    }
}
