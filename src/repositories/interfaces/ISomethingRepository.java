package repositories.interfaces;

import models.Something;

import java.util.List;
import java.util.Optional;

public interface ISomethingRepository {

    boolean insertFeedback(String feedback, int id);

    Optional<Something> getSomething(int id);

    List<Something> getAllSomethings();

    List<Something> getSomethingsByCategory(int categoryId);

    boolean deleteSomething(int id, String userRole);
}
