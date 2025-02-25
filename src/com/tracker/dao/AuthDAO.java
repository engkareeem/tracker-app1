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
    /* will be used by Manager not any user */
    public static void createAccount(HttpServlet servlet, Employee employee, String password) throws SQLException {
        String hashedPassword = hashPassword(password);

        System.out.println(hashedPassword);
        employee.setPassword(hashedPassword);
        EmployeeDAO.addEmployee(servlet, employee);
    }

    public static Employee login(HttpServlet servlet, String email, String password) throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance(servlet);

        if(dbConnection != null) {
            Connection connection = dbConnection.getConnection();
            String sql = """
                    SELECT id, password from employees
                    WHERE email=?
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                int id = resultSet.getInt("id");

                if(checkPassword(password, hashedPassword)) {
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

    private static String hashPassword(String password) {
        String salt = BCrypt.gensalt(12);

        return BCrypt.hashpw(password, salt);
    }

    private static boolean checkPassword(String password, String storedHash) {
        return BCrypt.checkpw(password, storedHash);
    }
}
