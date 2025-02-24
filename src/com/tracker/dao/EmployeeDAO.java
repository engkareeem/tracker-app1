package com.tracker.dao;

import com.tracker.model.*;
import com.tracker.util.DBConnection;
import jakarta.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public static void addEmployee(HttpServlet servlet, Employee employee) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);
        if (dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    INSERT INTO employees (name, contact, role_id, team_id)
                    VALUES (?, ?, ?, ?)
                    """;

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getContact());
            statement.setInt(3, employee.getRole().getId());
            statement.setInt(4, employee.getTeam().getId());
            statement.executeUpdate();
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    public static void removeEmployeeById(HttpServlet servlet, int id) {
        DBConnection dbConnection = DBConnection.getInstance(servlet);
        if (dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    DELETE FROM employees
                    WHERE id = ?
                    """;

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("DB Connection Error");
        }
    }

    public static void updateEmployee(HttpServlet servlet, Employee employee) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    UPDATE employees
                    SET name=?,contact=?,role_id=?,team_id=?
                    WHERE id=?
                    """;

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getContact());
            statement.setInt(3, employee.getRole().getId());
            statement.setInt(4, employee.getTeam().getId());

            statement.executeUpdate();
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    public static List<Employee> getEmployees(HttpServlet servlet) throws SQLException {
        ResultSet resultSet = getEmployeeByQuery(servlet, -1);
        List<Employee> employees = new ArrayList<>();

        while (resultSet.next()) {
            employees.add(getEmployeeFromSet(resultSet, servlet));
        }

        return employees;
    }

    public static Employee getEmployeeById(HttpServlet servlet, int id) throws SQLException {
        ResultSet resultSet = getEmployeeByQuery(servlet, id);

        if (resultSet.next()) {
            return getEmployeeFromSet(resultSet, servlet);
        } else {
            return null;
        }
    }

    private static Employee getEmployeeFromSet(ResultSet resultSet, HttpServlet servlet) throws SQLException {
        int roleId = resultSet.getInt("role_id");
        String roleName = resultSet.getString("role");
        Role role = new Role(roleId, roleName);

        int leaderId = resultSet.getInt("leader_id");
        String leaderName = resultSet.getString("leader_name");
        Employee leader = new Employee(leaderId, leaderName);

        int teamId = resultSet.getInt("team_id");
        String teamName = resultSet.getString("team_name");
        Team team = new Team(teamId, teamName, leader);


        int employeeId = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String contact = resultSet.getString("contact");

        List<Task> tasks = TaskDAO.getEmployeeTasks(servlet, employeeId);

        return new Employee(employeeId, name, contact, role, team, tasks);
    }

    private static ResultSet getEmployeeByQuery(HttpServlet servlet, int id) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);
        if (dbConnection == null) {
            throw new SQLException("DB Connection Error");
        } else {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    SELECT e.id, e.name, e.contact, r.id as role_id, r.role as role,
                    e.team_id, t.name as team_name, leader.id as leader_id, leader.name as leader_name
                    FROM employees as e
                    JOIN roles r ON e.role_id = r.id
                    LEFT JOIN teams t ON t.id = e.team_id
                    LEFT JOIN employees as leader ON t.leader_id = leader.id
                    """;
            if (id != -1) {
                sql += " WHERE e.id = ?";
            } else {
                sql += " ORDER BY e.id";
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            if (id != -1) {
                statement.setInt(1, id);
            }
            return statement.executeQuery();
        }
    }
}
