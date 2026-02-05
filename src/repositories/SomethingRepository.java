package repositories;

import data.interfaces.IDB;
import models.Something;
import repositories.interfaces.ISomethingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SomethingRepository implements ISomethingRepository {
    private final IDB db;

    public SomethingRepository(IDB db) {
        this.db = db;
    }

    @Override
    public List<Something> getAllSomethings() {
        String sql = "SELECT id, name, feedback FROM something";
        List<Something> somethings = new ArrayList<>();

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Something something = new Something(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("feedback")
                );
                somethings.add(something);
            }

        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }

        return somethings;
    }

    @Override
    public List<Something> getSomethingsByCategory(int categoryId) {
        return List.of();
    }

    @Override
    public boolean deleteSomething(int id, String userRole) {
        return false;
    }

    @Override
    public Something getSomething(int id) {
        String sql = "SELECT id, name, feedback FROM something WHERE id=?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Something(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("feedback")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean insertFeedback(String feedback, int id) {
        String sql = "UPDATE something SET feedback=? WHERE id=?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, feedback);
            st.setInt(2, id);

            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return false;
        }
    }
}
