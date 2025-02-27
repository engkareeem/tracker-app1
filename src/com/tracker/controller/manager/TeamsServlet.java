package com.tracker.controller.manager;

import com.tracker.dao.EmployeeDAO;
import com.tracker.dao.TeamDAO;
import com.tracker.model.Employee;
import com.tracker.model.Team;
import com.tracker.util.Utils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TeamsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchParam = req.getParameter("search");
        String teamIdParam = req.getParameter("teamId");
        String sortParam = req.getParameter("sort");
        String orderParam = req.getParameter("order");

        String search = searchParam != null ? searchParam : "";
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        RequestDispatcher dispatcher;

        if (employee == null || employee.getRole().getId() != 1) {
            resp.sendRedirect("/");
            return;
        }

        if(sortParam == null) {
            sortParam = "id";
        }

        if(orderParam == null) {
            orderParam = "asc";
        }

        if (teamIdParam != null) {
            dispatcher = req.getRequestDispatcher("view/teamleader/team.jsp");

            int teamId = Integer.parseInt(teamIdParam);
            try {
                Team team = TeamDAO.getTeams(this, "", teamId).get(0);
                List<Employee> members = TeamDAO.getTeamMembers(this, team);
                members = Utils.sortEmployees(members, sortParam, orderParam);

                req.setAttribute("team", team);
                req.setAttribute("members", members);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            dispatcher = req.getRequestDispatcher("view/manager/teams.jsp");

            try {
                List<Team> teams = TeamDAO.getTeams(this, search, -1);
                teams = Utils.sortTeams(teams, sortParam, orderParam);

                req.setAttribute("teams", teams);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("view/manager/newTeam.jsp");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        String nameParam = req.getParameter("name");
        String leaderIdParam = req.getParameter("leaderId");

        if (employee == null || employee.getRole().getId() != 1) {
            resp.sendRedirect("/");
            return;
        }

        if (nameParam != null && leaderIdParam != null && !leaderIdParam.equals("-1")) {
            try {
                int leaderId = Integer.parseInt(leaderIdParam);
                Team newTeam = new Team(nameParam, leaderId);

                TeamDAO.createTeam(this, newTeam);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            resp.sendRedirect("/teams");
            return;
        } else {
            try {
                List<Employee> leaders = EmployeeDAO.getAvailableLeaders(this);

                req.setAttribute("leaders", leaders);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



        dispatcher.forward(req, resp);
    }
}
