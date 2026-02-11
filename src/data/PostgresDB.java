package data;

import data.interfaces.IDB;
import repositories.FeedbackRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private static PostgresDB instance;

    private final String url;
    private final String user;
    private final String password;

    private PostgresDB(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static PostgresDB getInstance(String url, String user, String password) {
        if (instance == null) instance = new PostgresDB(url, user, password);
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {

    }
}
