package com.tracker.controller.teamleader;

import com.tracker.dao.TeamDAO;
import com.tracker.model.Employee;
import com.tracker.util.Utils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyTeamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/teamleader/team.jsp");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        String sortParam = req.getParameter("sort");
        String orderParam = req.getParameter("order");

        if (employee != null) {
            if(employee.getTeam() == null || employee.getRole().getId() != 2) {
                resp.sendRedirect("/");
                return;
            }

            req.setAttribute("team", employee.getTeam());

            try {
                List<Employee> members = TeamDAO.getTeamMembers(this, employee.getTeam());

                if (sortParam != null && orderParam != null) {
                    members = Utils.sortEmployees(members, sortParam, orderParam);
                }

                req.setAttribute("members", members);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            req.setAttribute("members", new ArrayList<Employee>());
        }

        dispatcher.forward(req, resp);
    }
}
