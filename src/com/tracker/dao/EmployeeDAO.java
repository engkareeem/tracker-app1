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
                    VALUES (?, ?, ?, ?, null)
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getEmail());
            statement.setString(3, employee.getPassword());
            statement.setInt(4, employee.getRole().getId());

            statement.executeUpdate();
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    public static void removeEmployeeById(HttpServlet servlet, int id) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if (dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    DELETE FROM employees
                    WHERE id = ?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            statement.executeUpdate();
        } else {
            throw new RuntimeException("DB Connection Error");
        }
    }

    public static void updateEmployee(HttpServlet servlet, Employee employee) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if (dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            StringBuilder sql = new StringBuilder("UPDATE employees SET ");
            List<Object> params = new ArrayList<>();

            /* To make it behave like PATCH not PUT */
            if (employee.getName() != null) {
                sql.append("name = ?, ");
                params.add(employee.getName());
            }

            if (employee.getEmail() != null) {
                sql.append("email = ?, ");
                params.add(employee.getEmail());
            }

            if (employee.getRole() != null) {
                sql.append("role_id = ?, ");
                params.add(employee.getRole().getId());
            }

            if (employee.getTeam() != null) {
                sql.append("team_id = ?, ");
                params.add(employee.getTeam().getId());
            }

            if (employee.getName() == null && employee.getEmail() == null && employee.getRole() == null && employee.getTeam() == null) {
                return;
            }

            sql.setLength(sql.length() - 2);
            sql.append(" WHERE id = ?");
            params.add(employee.getId());

            PreparedStatement statement = connection.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof String) {
                    statement.setString(i + 1, (String) params.get(i));
                } else if (params.get(i) instanceof Integer) {
                    statement.setInt(i + 1, (Integer) params.get(i));
                } else if (params.get(i) == null) {
                    statement.setNull(i + 1, java.sql.Types.NULL);
                }
            }

            statement.executeUpdate();
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    public static List<Employee> getEmployees(HttpServlet servlet, String search, int id) throws SQLException {
        ResultSet resultSet = getEmployeeByQuery(servlet, search, id, -1);
        List<Employee> employees = new ArrayList<>();

        while (resultSet.next()) {
            employees.add(getEmployeeFromSet(resultSet, servlet));
        }

        return employees;
    }

    public static Employee getEmployeeById(HttpServlet servlet, int id) throws SQLException {
        ResultSet resultSet = getEmployeeByQuery(servlet, "", id, -1);

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

    private static ResultSet getEmployeeByQuery(HttpServlet servlet, String search, int id, int role) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if (dbConnection == null) {
            throw new SQLException("DB Connection Error");
        } else {
            Connection connection = dbConnection.getConnection();
            List<Object> params = new ArrayList<>();
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
                params.add(id);
            } else {
                if (role != -1) {
                    sql += " WHERE r.id = ?";
                    params.add(role);
                } else {
                    sql += """
                            WHERE UPPER(e.name) LIKE UPPER(?) OR
                            UPPER(e.email) LIKE UPPER(?)
                            """;
                    params.add("%" + search + "%");
                    params.add("%" + search + "%");
                }

                sql += " ORDER BY e.id";
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof String) {
                    statement.setString(i + 1, (String) params.get(i));
                } else if (params.get(i) instanceof Integer) {
                    statement.setInt(i + 1, (Integer) params.get(i));
                }
            }

            return statement.executeQuery();
        }
    }
}
