package com.tracker.controller.teamleader;

import com.tracker.dao.TaskDAO;
import com.tracker.dao.TeamDAO;
import com.tracker.model.Employee;
import com.tracker.model.Task;
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
import java.util.ArrayList;
import java.util.List;

public class TeamTasksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/teamleader/teamTasks.jsp");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        String sortParam = req.getParameter("sort");
        String orderParam = req.getParameter("order");
        String teamIdParam = req.getParameter("teamId");
        String employeeIdParam = req.getParameter("employeeId");
        String searchParam = req.getParameter("search");

        if (sortParam == null) {
            sortParam = "ID";
        }

        if (orderParam == null) {
            orderParam = "asc";
        }

        if (employee != null && teamIdParam != null) {
            int teamId = Integer.parseInt(teamIdParam);

            if((employee.getRole().getId() != 2 && employee.getTeam().getId() != teamId) && employee.getRole().getId() != 1) {
                resp.sendRedirect("/");
                return;
            }

            try {
                String search = searchParam != null ? searchParam : "";
                Team team = TeamDAO.getTeams(this, "", teamId).get(0);
                List<Task> tasks = TaskDAO.getTeamTasks(this, team, search, employeeIdParam);

                tasks = Utils.sortTasks(tasks, sortParam, orderParam);

                req.setAttribute("tasks", tasks);
                req.setAttribute("team", team);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IndexOutOfBoundsException e) {
                resp.sendRedirect("/");
                return;
            }
        } else {
            resp.sendRedirect("/");
            return;
        }

        dispatcher.forward(req, resp);
    }
}
