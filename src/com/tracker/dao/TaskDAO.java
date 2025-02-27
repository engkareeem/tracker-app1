package com.tracker.dao;

import com.tracker.model.Employee;
import com.tracker.model.Task;
import com.tracker.model.TaskStatus;
import com.tracker.model.Team;
import com.tracker.util.DBConnection;
import jakarta.servlet.http.HttpServlet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    public static List<Task> getEmployeeTasks(HttpServlet servlet, int employeeId) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);
        if (dbConnection == null) {
            throw new SQLException("DB Connection Error");
        } else {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    SELECT t.id, t.title, t.description, t.status
                    FROM tasks as t
                    WHERE t.assigned_to = ?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                int status = resultSet.getInt("status");
                Task task = new Task(taskId, title, description, TaskStatus.fromValue(status));
                tasks.add(task);
            }
            return tasks;
        }
    }

    public static List<Task> getTeamTasks(HttpServlet servlet, int teamId, String search, String searchId) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    SELECT t.id, t.title, t.description, t.status, e.id as employee_id,
                    e.name as employee_name, e.email as employee_email, e.team_id as team_id
                    FROM tasks as t
                    JOIN employees as e ON t.assigned_to = e.id
                    WHERE team_id = ? AND (UPPER(t.title) LIKE UPPER(?) OR UPPER(t.description)
                     LIKE UPPER(?) OR UPPER(e.name) LIKE UPPER(?))
                    """;

            if(searchId != null && !searchId.isEmpty()) {
                sql += " AND e.id = ?";
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            List<Task> tasks = new ArrayList<>();

            statement.setInt(1, teamId);
            statement.setString(2, "%" + search + "%");
            statement.setString(3, "%" + search + "%");
            statement.setString(4, "%" + search + "%");

            if(searchId != null && !searchId.isEmpty()) {
                statement.setInt(5, Integer.parseInt(searchId));
            }

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                int status = resultSet.getInt("status");
                int employeeId = resultSet.getInt("employee_id");
                String employeeName = resultSet.getString("employee_name");
                String employeeEmail = resultSet.getString("employee_email");
                Employee employee = new Employee(employeeId, employeeName, employeeEmail);
                Task task = new Task(taskId, title, description, TaskStatus.fromValue(status), employee);

                tasks.add(task);
            }

            return tasks;
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    public static void addTask(HttpServlet servlet, Task task, int employeeId) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    INSERT INTO tasks(title, description, assigned_to)
                    values(?, ?, ?)
                    """;
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setInt(3, employeeId);

            int affectedRows = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if(affectedRows > 0) {
                if(generatedKeys.next()) {
                    int taskId = generatedKeys.getInt(1);

                    task.setId(taskId);
                } else {
                    task.setId(-1);
                }
            }
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    public static void updateTask(HttpServlet servlet, Task task) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);
        List<Object> parameters = new ArrayList<>();

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();

            StringBuilder sql = new StringBuilder("UPDATE tasks SET");

            if(task.getTitle() != null) {
                sql.append(" title=?, ");
                parameters.add(task.getTitle());
            }

            if(task.getDescription() != null) {
                sql.append(" description=?, ");
                parameters.add(task.getDescription());
            }

            if(task.getStatus() != null) {
                sql.append(" status=?, ");
                parameters.add(task.getStatus());
            }

            sql.setLength(sql.length() - 2);
            sql.append(" WHERE id=?");
            parameters.add(task.getId());

            PreparedStatement statement = connection.prepareStatement(sql.toString());

            for (int i = 0; i < parameters.size(); i++) {
                if(parameters.get(i) instanceof String) {
                    statement.setString(i + 1, (String) parameters.get(i));
                } else if(parameters.get(i) instanceof TaskStatus) {
                    statement.setInt(i + 1, ((TaskStatus) parameters.get(i)).getValue());
                } else if(parameters.get(i) instanceof Integer) {
                    statement.setInt(i + 1, (Integer) parameters.get(i));
                }
            }

            statement.executeUpdate();
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    public static void removeTask(HttpServlet servlet, int taskId) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    DELETE FROM tasks
                    WHERE id=?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, taskId);

            statement.executeUpdate();
        } else {
            throw new SQLException("DB Connection Error");
        }
    }
}
