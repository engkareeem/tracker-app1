package com.tracker.dao;

import com.tracker.model.Employee;
import com.tracker.util.DBConnection;
import jakarta.servlet.http.HttpServlet;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAO {
    /* will be used only by Manager  */
    public static void createAccount(HttpServlet servlet, Employee employee, String password) throws SQLException {
        String hashedPassword = hashPassword(password);

        employee.setPassword(hashedPassword);
        EmployeeDAO.addEmployee(servlet, employee);
    }

    public static Employee login(HttpServlet servlet, String email, String password) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if (dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    SELECT id, password from employees
                    WHERE email=?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                int id = resultSet.getInt("id");

                if (checkPassword(password, hashedPassword)) {
                    return EmployeeDAO.getEmployeeById(servlet, id);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    public static void updatePassword(HttpServlet servlet, int employeeId, String oldPassword, String newPassword) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if (dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    SELECT password from employees
                    WHERE id=?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, employeeId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");

                if (checkPassword(oldPassword, hashedPassword)) {
                    String newHashedPassword = hashPassword(newPassword);
                    sql = """
                            UPDATE employees
                            SET password=?
                            WHERE id=?
                            """;
                    statement = connection.prepareStatement(sql);

                    statement.setString(1, newHashedPassword);
                    statement.setInt(2, employeeId);

                    statement.executeUpdate();
                } else {
                    throw new SQLException("Old password is incorrect");
                }
            } else {
                throw new SQLException("Employee not found");
            }
        } else {
            throw new SQLException("DB Connection Error");
        }
    }

    private static String hashPassword(String password) {
        String salt = BCrypt.gensalt(12);

        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkPassword(String password, String storedHash) {
        return BCrypt.checkpw(password, storedHash);
    }
}
