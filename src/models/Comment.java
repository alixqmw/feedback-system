package models;

import java.time.LocalDateTime;

public class Comment {
    private final int id;
    private final String text;
    private final LocalDateTime createdAt;
    private final int userId;
    private final int feedbackId;

    public Comment(int id, String text, LocalDateTime createdAt, int userId, int feedbackId) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.userId = userId;
        this.feedbackId = feedbackId;
    }

    public int getId() { return id; }
    public String getText() { return text; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getUserId() { return userId; }
    public int getFeedbackId() { return feedbackId; }
}