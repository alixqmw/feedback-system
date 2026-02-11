package repositories;

import data.PostgresDB;
import models.Role;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private final PostgresDB db;

    public UserRepository(PostgresDB db) {
        this.db = db;
    }

    public User findByEmailAndPassword(String email, String password) {
        String sql = """
            SELECT u.id, u.name, u.email, u.password, r.id AS r_id, r.name AS r_name
            FROM users u
            JOIN roles r ON r.id = u.role_id
            WHERE u.email = ? AND u.password = ?
        """;

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, email);
            st.setString(2, password);

            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) return null;

                Role role = new Role(rs.getInt("r_id"), rs.getString("r_name"));
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        role
                );
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return null;
        }
    }

    // Registration: creates user with USER role
    public boolean createUser(String name, String email, String password) {
        String sql = """
            INSERT INTO users(name, email, password, role_id)
            VALUES (?, ?, ?, (SELECT id FROM roles WHERE name = 'USER'))
        """;

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, name);
            st.setString(2, email);
            st.setString(3, password);

            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return false;
        }
    }
}
