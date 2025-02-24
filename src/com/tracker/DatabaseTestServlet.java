package com.tracker;

import com.tracker.dao.AuthDAO;
import com.tracker.dao.EmployeeDAO;
import com.tracker.dao.TeamDAO;
import com.tracker.model.Employee;
import com.tracker.model.Team;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class DatabaseTestServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            List<Employee> employees = EmployeeDAO.getEmployees(this);
            if(employees.isEmpty()) {
                out.println("<h1>Database Error</h1>");
                return;
            }
            for(Employee employee : employees) {
                if(employee == null) {
                    out.println("<h1>Employee not found</h1>");
                    return;
                }
                out.println("<h1>Employee Details</h1>");
                out.println("<p>ID: " + employee.getId() + "</p>");
                out.println("<p>Name: " + employee.getName() + "</p>");
                out.println("<p>Contact: " + employee.getEmail() + "</p>");
                out.println("<p>Role: " + employee.getRole().getName() + "</p>");
                out.println("<p>Team: " + employee.getTeam().getName() + "</p>");
                out.println("<h2>Tasks</h2>");
                out.println("<ul>");
                for (int i = 0; i < employee.getTasks().size(); i++) {
                    out.println("<li>" + employee.getTasks().get(i).getTitle()
                            + " | " +
                            employee.getTasks().get(i).getDescription()
                            + " | " +
                            employee.getTasks().get(i).getStatus().getLabel()
                            + "</li>");
                }
                out.println("</ul>");
            }
        } catch (SQLException e) {
            out.println("<h1>Database Error</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if(email != null && password != null) {
            try {
                Employee employee = AuthDAO.login(this, email, password);
                if(employee == null) {
                    resp.getWriter().println("<h1>Incorrect email or password</h1>");
                } else {
                    resp.getWriter().println("<h1>WELCOME!</h1>");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
