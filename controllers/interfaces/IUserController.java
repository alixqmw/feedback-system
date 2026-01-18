package com.company.controllers.interfaces;

public interface IFeedbackController {
    String createFeedback(String name, int rating, String comment);
    String getFeedback(int id);
    String getAllFeedbacks();
}
