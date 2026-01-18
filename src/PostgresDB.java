package data;

import data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/feedback_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres"; // change if needed

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to PostgreSQL", e);
        }
    }
}
