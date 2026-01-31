package repositories;

import models.Something;
import data.interfaces.IDB;
import models.User;
import repositories.interfaces.ISomethingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SomethingRepository implements ISomethingRepository {
    private final IDB db;

    public SomethingRepository(IDB db) { this.db = db; }

    public List<Something> getAllSomethings() {
        Connection con = null;

        try {
            con = db.getConnection();
            String sql = "SELECT id,name,feedback FROM something";
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);
            List<Something> Somethings = new ArrayList<>();
            while (rs.next()) {
                Something Something = new Something(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("feedback")
                );

                Somethings.add(Something);
            }

            return Somethings;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }

        return null;
    }

    public Something getSomething(int id) {
        Connection con = null;

        try {
            con = db.getConnection();
            String sql = "SELECT id,name, feedback FROM something WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Something(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("feedback")
                );
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }

        return null;
    }
    @Override
    public boolean insertFeedback(String feedback, int id) {
        Connection con = null;

        try {
            con = db.getConnection();
            String sql = "UPDATE something SET feedback=? WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, feedback);
            st.setInt(2, id);

            st.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }

        return false;
    }
}
