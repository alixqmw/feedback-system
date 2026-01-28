package controllers.interfaces;

public interface ISomethingController {
    String getSomething(int id);
    String getAllSomethings();
    String insertFeedback(String feedback, int id);
}
