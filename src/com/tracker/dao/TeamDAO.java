package com.tracker.dao;

import com.tracker.model.Employee;
import com.tracker.model.Team;
import com.tracker.util.DBConnection;
import jakarta.servlet.http.HttpServlet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {
    public static void setEmployeeToTeam(HttpServlet servlet,
                                         int teamId, int employeeId) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    UPDATE Employees
                    SET team_id = ?
                    WHERE id = ?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, teamId);
            statement.setInt(2, employeeId);

            statement.executeUpdate();
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    public static List<Employee> getTeamMembers(HttpServlet servlet, Team team) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    SELECT e.id, e.name, e.email
                    FROM employees as e
                    WHERE not e.id = ? AND team_id = ?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            List<Employee> members = new ArrayList<>();

            statement.setInt(1, team.getLeader().getId());
            statement.setInt(2, team.getId());

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Employee employee = new Employee(id, name, email);

                members.add(employee);
            }

            return members;
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    public static void createTeam(HttpServlet servlet, Team team) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    INSERT INTO teams (name, leader_id)
                    VALUES(?, ?)
                    """;
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, team.getName());
            statement.setInt(2, team.getLeader().getId());

            int affectedRows = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if(affectedRows > 0) {
                if(generatedKeys.next()) {
                    int teamId = generatedKeys.getInt(1);

                    setEmployeeToTeam(servlet, teamId, team.getLeader().getId());
                }
            }
        }
    }

    public static void updateTeam(HttpServlet servlet, Team team) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    UPDATE teams
                    SET name=?, leader_id=?
                    WHERE id=?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, team.getName());
            statement.setInt(2, team.getLeader().getId());
            statement.setInt(3, team.getId());

            statement.executeUpdate();
        }
    }

    public static void removeTeam(HttpServlet servlet, int teamId) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    DELETE FROM teams
                    WHERE id=?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, teamId);

            statement.executeUpdate(); /* Every Employee associated with this team will have NULL */
        } else {
            throw new SQLException("DB Connection Error");
        }
    }
}
