package com.tracker.dao;

import com.tracker.model.Task;
import com.tracker.model.TaskStatus;
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

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    UPDATE tasks
                    set title=?, description=?, status=?
                    WHERE id=?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setInt(3, task.getStatus().getValue());
            statement.setInt(4, task.getId());

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
