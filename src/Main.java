import data.PostgresDB;
import dto.FullFeedbackDTO;
import dto.UserDTO;
import repositories.FeedbackRepository;
import repositories.UserRepository;
import services.AuthService;
import services.FeedbackService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PostgresDB db = PostgresDB.getInstance(
                "jdbc:postgresql://localhost:5432/feedback_system",
                "postgres",
                "0000"
        );

        UserRepository userRepo = new UserRepository(db);
        FeedbackRepository feedbackRepo = new FeedbackRepository(db);

        AuthService auth = new AuthService(userRepo);
        FeedbackService feedbackService = new FeedbackService(feedbackRepo);

        Scanner sc = new Scanner(System.in);

        UserDTO currentUser = null;
        Integer lastFeedbackId = null;

        while (true) {
            System.out.println("\n=== FEEDBACK SYSTEM ===");
            System.out.println("Logged in: " + (currentUser == null ? "NO" : currentUser.email() + " (" + currentUser.role() + ")"));
            System.out.println("Last feedback id: " + (lastFeedbackId == null ? "-" : lastFeedbackId));

            System.out.println("\nChoose the option (0-6):");
            System.out.println("0 - Create user");
            System.out.println("1 - Login");
            System.out.println("2 - Make feedback");
            System.out.println("3 - Add comment to last feedback");
            System.out.println("4 - View full last feedback");
            System.out.println("5 - Logout");
            System.out.println("6 - Exit");
            System.out.print("> ");

            String opt = sc.nextLine().trim();

            switch (opt) {

                case "0" -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine().trim();

                    System.out.print("Email: ");
                    String email = sc.nextLine().trim();

                    System.out.print("Password: ");
                    String password = sc.nextLine().trim();

                    boolean created = auth.register(name, email, password); // <-- если метод иначе, поменяй тут
                    System.out.println(created ? "User created!" : "User was not created.");
                }

                case "1" -> {
                    if (currentUser != null) {
                        System.out.println("Already logged in.");
                        break;
                    }

                    System.out.print("Email: ");
                    String email = sc.nextLine().trim();

                    System.out.print("Password: ");
                    String password = sc.nextLine().trim();

                    currentUser = auth.login(email, password);
                    System.out.println("Logged in: " + currentUser);

                    if (currentUser == null) System.out.println("Login failed.");
                }

                case "2" -> {
                    if (currentUser == null) {
                        System.out.println("Please login first (option 1).");
                        break;
                    }

                    System.out.print("Title: ");
                    String title = sc.nextLine().trim();

                    System.out.print("Message: ");
                    String message = sc.nextLine().trim();

                    System.out.print("Category id: ");
                    int categoryId;
                    try {
                        categoryId = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong category id.");
                        break;
                    }

                    int id = feedbackService.createFeedback(currentUser, title, message, categoryId);
                    System.out.println("Created feedback id: " + id);

                    if (id > 0) lastFeedbackId = id;
                    else System.out.println("Feedback was not created (check SQL error above).");
                }

                case "3" -> {
                    if (currentUser == null) {
                        System.out.println("Please login first (option 1).");
                        break;
                    }
                    if (lastFeedbackId == null) {
                        System.out.println("No feedback yet. Create feedback first (option 2).");
                        break;
                    }

                    System.out.print("Comment text: ");
                    String text = sc.nextLine().trim();
                    if (text.isEmpty()) {
                        System.out.println("Empty comment. Skipped.");
                        break;
                    }

                    boolean ok = feedbackService.addComment(currentUser, lastFeedbackId, text);
                    System.out.println("Comment added: " + ok);
                }

                case "4" -> {
                    if (currentUser == null) {
                        System.out.println("Please login first (option 1).");
                        break;
                    }
                    if (lastFeedbackId == null) {
                        System.out.println("No feedback yet. Create feedback first (option 2).");
                        break;
                    }

                    FullFeedbackDTO full = feedbackService.getFull(lastFeedbackId, currentUser);
                    System.out.println("\nFULL FEEDBACK:");
                    System.out.println(full);

                    if (full == null) System.out.println("Full feedback is NULL (check SQL errors above).");
                }

                case "5" -> {
                    currentUser = null;
                    lastFeedbackId = null;
                    System.out.println("We are waiting for your next feedback.");
                }

                case "6" -> {
                    System.out.println("Bye:)");
                    return;
                }

                default -> System.out.println("Unknown option. Choose 0-6.");
            }
        }
    }
}