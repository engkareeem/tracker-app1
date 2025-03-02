package com.tracker.controller.manager;

import com.tracker.dao.EmployeeDAO;
import com.tracker.dao.TaskDAO;
import com.tracker.dao.TeamDAO;
import com.tracker.model.*;
import com.tracker.util.Utils;
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
        int searchId = -1;

        String sortParam = req.getParameter("sort");
        String orderParam = req.getParameter("order");
        String employeeIdParam = req.getParameter("employeeId");
        String searchParam = req.getParameter("search");

        if (sortParam == null) {
            sortParam = "ID";
        }

        if (orderParam == null) {
            orderParam = "asc";
        }

        if (employeeIdParam != null && !employeeIdParam.isEmpty()) {
            searchId = Integer.parseInt(employeeIdParam);
        }

        if (employee != null && employee.getRole().getId() == 1) {
            try {
                List<Employee> employees = EmployeeDAO.getEmployees(this, searchParam == null ? "" : searchParam, searchId);
                List<Team> teams = TeamDAO.getTeams(this, "", -1);

                employees = Utils.sortEmployees(employees, sortParam, orderParam);

                req.setAttribute("employees", employees);
                req.setAttribute("teams", teams);
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

            if (keyParts.length != 3) continue;
            String employeeId = keyParts[0];
            String original = keyParts[2];

            if (value.equals(original)) continue;

            Employee newEmployee = new Employee(Integer.parseInt(employeeId));
            if (keyParts[1].equals("role")) {
                if (original.equals("2")) {
                    try {
                        Employee employee = EmployeeDAO.getEmployeeById(this, Integer.parseInt(employeeId));
                        System.out.println("1Employee ID: " + employeeId);
                        if (employee == null || employee.getTeam().getName() != null) continue;

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    newEmployee.setTeam(new Team(null));
                }

                newEmployee.setRole(new Role(Integer.parseInt(value)));
            } else if (keyParts[1].equals("team")) {
                if (value.equals("none")) {
                    newEmployee.setTeam(new Team(null));
                } else {
                    newEmployee.setTeam(new Team(Integer.parseInt(value)));
                }
            }

            try {
                EmployeeDAO.updateEmployee(this, newEmployee);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        resp.sendRedirect(req.getHeader("referer"));
    }
}
