package models;

import java.time.LocalDateTime;

public class Feedback {
    private final int id;
    private final String title;
    private final String message;
    private final String status;
    private final LocalDateTime createdAt;
    private final int userId;
    private final int categoryId;

    public Feedback(int id, String title, String message, String status, LocalDateTime createdAt, int userId, int categoryId) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getUserId() { return userId; }
    public int getCategoryId() { return categoryId; }
}