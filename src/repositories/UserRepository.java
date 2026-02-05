package repositories;

import data.interfaces.IDB;
import models.User;
import repositories.interfaces.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean register(User user) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "INSERT INTO users(name, password) VALUES (?, ?)")) {

            st.setString(1, user.getname());
            st.setString(2, user.getPassword());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public User getUser(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT id, name, password FROM users WHERE id=?")) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT id, name, password FROM users")) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
        }
        return users;
    }

    @Override
    public User login(String name, String password) {
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT id, name, password FROM users WHERE name=? AND password=?")) {

            st.setString(1, name);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
        }
        return null;
    }
}
