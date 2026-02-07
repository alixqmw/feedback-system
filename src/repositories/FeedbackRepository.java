package repositories;

import db.PostgresDB;
import dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackRepository<PostgresDB, PostgresDB> {

    private final PostgresDB db;

    public <PostgresDB> FeedbackRepository(PostgresDB db) {
        this.db = db;
    }

    public int createFeedback(String title, String message, int userId, int categoryId) {
        String sql = """
            INSERT INTO feedback (title, message, status, user_id, category_id)
            VALUES (?, ?, 'OPEN', ?, ?)
            RETURNING id
        """;

        try (Connection con = db.getClass();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, title);
            st.setString(2, message);
            st.setInt(3, userId);
            st.setInt(4, categoryId);

            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt("id") : -1;

        } catch (SQLException e) {
            System.err.println("SQL error (createFeedback): " + e.getMessage());
            return -1;
        }
    }

    public boolean addComment(int feedbackId, int userId, String text) {
        String sql = """
            INSERT INTO comments (text, user_id, feedback_id)
            VALUES (?, ?, ?)
        """;

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, text);
            st.setInt(2, userId);
            st.setInt(3, feedbackId);

            return st.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("SQL error (addComment): " + e.getMessage());
            return false;
        }
    }

    public boolean updateStatus(int feedbackId, FeedbackStatus status) {
        String sql = "UPDATE feedback SET status = ? WHERE id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, status.name());
            st.setInt(2, feedbackId);

            return st.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("SQL error (updateStatus): " + e.getMessage());
            return false;
        }
    }

    public FullFeedbackDTO getFullFeedbackDescription(int feedbackId) {

        String feedbackSql = """
            SELECT
                f.id, f.title, f.message, f.status, f.created_at,
                u.id AS u_id, u.name AS u_name, u.email AS u_email,
                r.name AS role_name,
                c.id AS c_id, c.name AS c_name
            FROM feedback f
            JOIN users u ON u.id = f.user_id
            JOIN roles r ON r.id = u.role_id
            JOIN categories c ON c.id = f.category_id
            WHERE f.id = ?
        """;

        String commentsSql = """
            SELECT
                cm.id, cm.text, cm.created_at,
                u.id AS u_id, u.name AS u_name, u.email AS u_email,
                r.name AS role_name
            FROM comments cm
            JOIN users u ON u.id = cm.user_id
            JOIN roles r ON r.id = u.role_id
            WHERE cm.feedback_id = ?
            ORDER BY cm.created_at ASC
        """;

        try (Connection con = db.getConnection()) {

            UserDTO author;
            CategoryDTO category;
            int id;
            String title;
            String message;
            String status;
            Timestamp createdAt;

            try (PreparedStatement st = con.prepareStatement(feedbackSql)) {
                st.setInt(1, feedbackId);
                ResultSet rs = st.executeQuery();

                if (!rs.next()) return null;

                id = rs.getInt("id");
                title = rs.getString("title");
                message = rs.getString("message");
                status = rs.getString("status");
                createdAt = rs.getTimestamp("created_at");

                author = new UserDTO(
                        rs.getInt("u_id"),
                        rs.getString("u_name"),
                        rs.getString("u_email"),
                        rs.getString("role_name")
                );

                category = new CategoryDTO(
                        rs.getInt("c_id"),
                        rs.getString("c_name")
                );
            }

            List<CommentDTO> comments = new ArrayList<>();

            try (PreparedStatement st = con.prepareStatement(commentsSql)) {
                st.setInt(1, feedbackId);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    UserDTO cAuthor = new UserDTO(
                            rs.getInt("u_id"),
                            rs.getString("u_name"),
                            rs.getString("u_email"),
                            rs.getString("role_name")
                    );

                    comments.add(new CommentDTO(
                            rs.getInt("id"),
                            rs.getString("text"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            cAuthor
                    ));
                }
            }

            return new FullFeedbackDTO(
                    id,
                    title,
                    message,
                    status,
                    createdAt.toLocalDateTime(),
                    author,
                    category,
                    comments
            );

        } catch (SQLException e) {
            System.err.println("SQL error (getFullFeedbackDescription): " + e.getMessage());
            return null;
        }
    }
}
