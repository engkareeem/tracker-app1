package com.tracker.util;

import jakarta.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance = null;
    private Connection connection = null;

    private DBConnection() {
    }

    private void init(String url, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        this.connection = DriverManager.getConnection(url, user, password);

        System.out.println("DBConnection Initialized");
    }

    public Connection getConnection() {
        return this.connection;
    }

    public static DBConnection getInstance(HttpServlet servlet) {
        if (instance != null && instance.getConnection() != null) {
            return instance;
        } else {
            String url = servlet.getServletContext().getInitParameter("jdbcUrl");
            String user = servlet.getServletContext().getInitParameter("dbUser");
            String password = servlet.getServletContext().getInitParameter("dbPassword");


            try {
                instance = new DBConnection();
                instance.init(url, user, password);

                if(instance.getConnection() == null) {
                    instance = null;
                }
                return instance;
            } catch (Exception e) {
                return null; /* Connection failed */
            }
        }
    }
}
