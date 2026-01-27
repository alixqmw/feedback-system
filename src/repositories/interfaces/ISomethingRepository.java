package repositories.interfaces;

import models.Something;

import java.util.List;

public interface ISomethingRepository {
    boolean insertFeedback(String feedback, int id);
    Something getSomething(int id);
    List<Something> getAllSomethings();
}
