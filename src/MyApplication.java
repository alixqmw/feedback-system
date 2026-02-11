import data.PostgresDB;
import dto.FullFeedbackDTO;
import dto.UserDTO;
import repositories.FeedbackRepository;
import repositories.UserRepository;
import services.AuthService;
import services.FeedbackService;

import java.util.Scanner;

public class MyApplication {

    private static final Scanner sc = new Scanner(System.in);

    private static UserDTO currentUser = null;
    private static Integer lastFeedbackId = null;

    public static void main(String[] args) {

        PostgresDB db = PostgresDB.getInstance(
                "jdbc:postgresql://localhost:5432/feedback_system",
                "postgres",
                "0000"
        );

        AuthService auth = new AuthService(new UserRepository(db));
        FeedbackService feedbackService = new FeedbackService(new FeedbackRepository(db));

        while (true) {
            System.out.println("\n0-Register | 1-Login | 2-Feedback | 3-Comment | 4-View | 5-Logout | 6-Exit");
            System.out.print("> ");
            String opt = sc.nextLine();

            switch (opt) {

                case "0" -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Password: ");
                    String pass = sc.nextLine();

                    System.out.println(auth.register(name, email, pass)
                            ? "User created"
                            : "User not created");
                }

                case "1" -> {
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Password: ");
                    String pass = sc.nextLine();

                    currentUser = auth.login(email, pass);
                    System.out.println(currentUser);
                }

                case "2" -> {
                    if (currentUser == null) break;

                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Message: ");
                    String msg = sc.nextLine();
                    System.out.print("Category id: ");
                    int catId = Integer.parseInt(sc.nextLine());

                    lastFeedbackId = feedbackService.createFeedback(currentUser, title, msg, catId);
                    System.out.println("Feedback id = " + lastFeedbackId);
                }

                case "3" -> {
                    if (currentUser == null || lastFeedbackId == null) break;

                    System.out.print("Comment: ");
                    String text = sc.nextLine();
                    feedbackService.addComment(currentUser, lastFeedbackId, text);
                }

                case "4" -> {
                    if (currentUser == null || lastFeedbackId == null) break;

                    FullFeedbackDTO full = feedbackService.getFull(lastFeedbackId, currentUser);
                    System.out.println(full);
                }

                case "5" -> {
                    currentUser = null;
                    lastFeedbackId = null;
                }

                case "6" -> {
                    return;
                }
            }
        }
    }
}