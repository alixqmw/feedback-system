package com.company.controllers;

import com.company.models.Feedback;
import com.company.controllers.interfaces.IFeedbackController;
import com.company.repositories.interfaces.IFeedbackRepository;

import java.util.List;

public class FeedbackController implements IFeedbackController {

    private final IFeedbackRepository repo;

    public FeedbackController(IFeedbackRepository repo) { // Dependency Injection
        this.repo = repo;
    }

    public String createFeedback(String name, int rating, String comment) {
        Feedback feedback = new Feedback(name, rating, comment);

        boolean created;
        created = repo.createFeedback(feedback);

        return created ? "Feedback was created!" : "Feedback creation failed!";
    }

    public String getFeedback(int id) {
        Feedback feedback;
        feedback = repo.getFeedback(id);

        return (feedback == null ? "Feedback was not found!" : feedback.toString());
    }

    public String getAllFeedbacks() {
        List<Feedback> feedbacks = repo.getAllFeedbacks();

        StringBuilder response = new StringBuilder();
        for (Feedback feedback : feedbacks) {
            response.append(feedback.toString()).append("\n");
        }

        return response.toString();
    }
}
