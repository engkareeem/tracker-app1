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
                    INSERT INTO employees (name, email, password, role_id, team_id)
                    VALUES (?, ?, ?, ?, ?)
                    """;

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getEmail());
            statement.setString(3, employee.getPassword());
            statement.setInt(4, employee.getRole().getId());
            statement.setInt(5, employee.getTeam().getId());
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

        if (dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    UPDATE employees
                    SET name=?,email=?,role_id=?,team_id=?
                    WHERE id=?
                    """;

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getEmail());
            statement.setInt(3, employee.getRole().getId());
            statement.setInt(4, employee.getTeam().getId());

            statement.executeUpdate();
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    public static List<Employee> getEmployees(HttpServlet servlet, int role) throws SQLException {
        ResultSet resultSet = getEmployeeByQuery(servlet, -1, role);
        List<Employee> employees = new ArrayList<>();

        while (resultSet.next()) {
            employees.add(getEmployeeFromSet(resultSet, servlet));
        }

        return employees;
    }

    public static Employee getEmployeeById(HttpServlet servlet, int id) throws SQLException {
        ResultSet resultSet = getEmployeeByQuery(servlet, id, -1);

        if (resultSet.next()) {
            return getEmployeeFromSet(resultSet, servlet);
        } else {
            return null;
        }
    }

    public static List<Employee> getAvailableLeaders(HttpServlet servlet) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);
        if (dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    SELECT e.id, e.name, e.email
                    FROM employees as e
                    WHERE e.role_id = 2 AND e.team_id IS NULL
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<Employee> employees = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                employees.add(new Employee(id, name, email));
            }

            return employees;
        } else {
            throw new RuntimeException("DB Connection Error");
        }
    }

    private static Employee getEmployeeFromSet(ResultSet resultSet, HttpServlet servlet) throws SQLException {
        int roleId = resultSet.getInt("role_id");
        String roleName = resultSet.getString("role");
        Role role = new Role(roleId, roleName);

        int leaderId = resultSet.getInt("leader_id");
        String leaderName = resultSet.getString("leader_name");
        String leaderEmail = resultSet.getString("leader_email");
        Employee leader = new Employee(leaderId, leaderName, leaderEmail);

        int teamId = resultSet.getInt("team_id");
        String teamName = resultSet.getString("team_name");
        Team team = new Team(teamId, teamName, leader);


        int employeeId = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");

        return new Employee(employeeId, name, email, role, team);
    }

    private static ResultSet getEmployeeByQuery(HttpServlet servlet, int id, int role) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);
        if (dbConnection == null) {
            throw new SQLException("DB Connection Error");
        } else {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    SELECT e.id, e.name, e.email, r.id as role_id, r.role as role,
                    e.team_id, t.name as team_name, leader.id as leader_id,
                    leader.name as leader_name, leader.email as leader_email
                    FROM employees as e
                    JOIN roles r ON e.role_id = r.id
                    LEFT JOIN teams t ON t.id = e.team_id
                    LEFT JOIN employees as leader ON t.leader_id = leader.id
                    """;
            if (id != -1) {
                sql += " WHERE e.id = ?";
            } else {
                if (role != -1) {
                    sql += " WHERE r.id = ?";
                }
                sql += " ORDER BY e.id";
            }


            PreparedStatement statement = connection.prepareStatement(sql);
            if (id != -1) {
                statement.setInt(1, id);
            }

            if (role != -1) {
                statement.setInt(1, role);
            }

            return statement.executeQuery();
        }
    }
}
