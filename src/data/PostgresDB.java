package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB {
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
        return DriverManager.getConnection(url, user, password);
    }
}
