package services;

import dto.FullFeedbackDTO;
import dto.UserDTO;
import repositories.FeedbackRepository;

import java.util.Set;

public class FeedbackService {
    private final FeedbackRepository repo;

    private static final Set<String> ALLOWED_STATUS =
            Set.of("OPEN", "IN_PROGRESS", "DONE", "REJECTED");

    public FeedbackService(FeedbackRepository repo) {
        this.repo = repo;
    }

    public String validateFeedback(String title, String message) {
        if (title == null || title.trim().length() < 3 || title.trim().length() > 120) return "Invalid title";
        if (message == null || message.trim().length() < 10 || message.trim().length() > 2000) return "Invalid message";
        return null;
    }

    public int createFeedback(UserDTO currentUser, String title, String message, int categoryId) {
        String err = validateFeedback(title, message);
        if (err != null) return -1;

        return repo.createFeedback(title.trim(), message.trim(), currentUser.id(), categoryId);
    }

    public FullFeedbackDTO getFull(int feedbackId) {
        if (feedbackId <= 0) return null;
        return repo.getFullFeedbackDescription(feedbackId);
    }

    public boolean addComment(UserDTO currentUser, int feedbackId, String text) {
        if (feedbackId <= 0) return false;
        if (text == null || text.trim().length() < 2) return false;

        return repo.addComment(feedbackId, currentUser.id(), text.trim());
    }

    public boolean changeStatus(UserDTO currentUser, int feedbackId, String status) {
        if (feedbackId <= 0) return false;
        if (status == null || !ALLOWED_STATUS.contains(status)) return false;

        boolean can = currentUser.role().equals("ADMIN") || currentUser.role().equals("MANAGER");
        if (!can) return false;

        return repo.updateStatus(feedbackId, status);
    }

    // lambda example: count comments by MANAGERS in full DTO
    public long countManagerComments(FullFeedbackDTO full) {
        return full.comments().stream()
                .filter(c -> "MANAGER".equals(c.author().role()))
                .count();
    }
}
