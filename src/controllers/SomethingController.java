package controllers;

import controllers.interfaces.ISomethingController;
import models.Something;
import repositories.interfaces.ISomethingRepository;
import java.util.List;

public class SomethingController implements ISomethingController {
    private final ISomethingRepository repo;

    public SomethingController(ISomethingRepository repo) { // Dependency Injection
        this.repo = repo;
    }
    public String insertFeedback(String feedback, int id) {
        //Something something = new Something(name, feedback);

        boolean created = repo.insertFeedback(feedback,id);

        return (created ? "User was created!" : "User creation was failed!");
    }
    public String getAllSomethings() {
        List<Something> Somethings = repo.getAllSomethings();

        StringBuilder response = new StringBuilder();
        for (Something Something : Somethings) {
            response.append(Something.toString()).append("\n");
        }

        return response.toString();
    }
    public String getSomething(int id) {
        Something Something = repo.getSomething(id);

        return (Something == null ? "User was not found!" : Something.toString());
    }
}
