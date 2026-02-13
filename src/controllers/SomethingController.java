package controllers;

import controllers.interfaces.ISomethingController;
import models.Something;
import repositories.interfaces.ISomethingRepository;
import java.util.List;

public class SomethingController implements ISomethingController {
    private final ISomethingRepository repo;

    public SomethingController(ISomethingRepository repo) {
        this.repo = repo;
    }

    public String insertFeedback(String feedback, int id) {
        if (feedback == null || feedback.trim().isEmpty()) {
            return "Feedback is empty";
        }
        boolean ok = repo.insertFeedback(feedback, id);
        return (ok ? "Success" : "Failed");
    }

    public String getAllSomethings() {
        List<Something> somethings = repo.getAllSomethings();
        if (somethings.isEmpty()) return "No data";

        StringBuilder sb = new StringBuilder();
        sb.append("\nID | Name | Feedback\n");
        sb.append("--------------------\n");

        for (Something s : somethings) {
            String fb = (s.getFeedback() == null) ? "-" : s.getFeedback();
            sb.append(String.format("%d | %s | %s\n", s.getId(), s.getName(), fb));
        }
        return sb.toString();
    }

    public String getSomething(int id) {
        Something s = repo.getSomething(id);
        return (s == null ? "Not found" : s.toString());
    }

    public String deleteSomething(int id, String role) {
        boolean ok = repo.deleteSomething(id, role);
        return (ok ? "Deleted" : "Error");
    }
}