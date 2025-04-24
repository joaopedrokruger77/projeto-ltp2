package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    public static Connection connect(String dbName) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false";
        String user = "root"; // Troque se seu usuário for outro
        String password = "Jp30062005"; // Troque pela sua senha do MySQL
        return DriverManager.getConnection(url, user, password);
    }

    public static Connection connectRoot() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/?useSSL=false";
        String user = "root";
        String password = "Jp30062005";
        return DriverManager.getConnection(url, user, password);
    }
}