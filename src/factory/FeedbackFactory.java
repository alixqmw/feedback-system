package factory;

import models.Feedback;

import java.time.LocalDateTime;

public class FeedbackFactory {
    private FeedbackFactory() {}

    public static Feedback createNew(String title, String message, int userId, int categoryId) {
        return new Feedback(0, title, message, "OPEN", LocalDateTime.now(), userId, categoryId);
    }
}