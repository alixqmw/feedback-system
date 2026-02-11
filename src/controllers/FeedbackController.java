package controllers;

import dto.UserDTO;
import services.FeedbackService;

public class FeedbackController {
    private final FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    public String getFullFeedback(int feedbackId, UserDTO currentUser) {
        var dto = service.getFull(feedbackId, currentUser);
        return (dto == null) ? "Not found / access denied" : dto.toString();
    }
}
