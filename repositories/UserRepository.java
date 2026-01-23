package repositories;

import data.interfaces.IDB;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    // Get all users from database
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, rating FROM users";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("rating")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
