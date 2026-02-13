import data.PostgresDB;
import dto.FullFeedbackDTO;
import dto.UserDTO;
import repositories.FeedbackRepository;
import repositories.UserRepository;
import repositories.SomethingRepository;
import controllers.SomethingController;
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
        SomethingController sController = new SomethingController(new SomethingRepository(db));

        while (true) {
            printStatus();
            System.out.println("0-Reg | 1-Login | 2-FB | 3-Comment | 4-View | 5-List | 6-Del | 7-Logout | 8-Exit");
            System.out.print("> ");
            String opt = sc.nextLine();

            switch (opt) {
                case "0" -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Email: ");
                    String email = sc.nextLine().trim();
                    System.out.print("Pass: ");
                    String pass = sc.nextLine();

                    if (!name.isEmpty() && !email.isEmpty()) {
                        System.out.println(auth.register(name, email, pass) ? "OK" : "Error");
                    }
                }

                case "1" -> {
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Pass: ");
                    String pass = sc.nextLine();
                    currentUser = auth.login(email, pass);
                    if (currentUser != null) System.out.println("Hello, " + currentUser.name());
                }

                case "2" -> {
                    if (isLogged()) {
                        System.out.print("Title: ");
                        String title = sc.nextLine();
                        System.out.print("Msg: ");
                        String msg = sc.nextLine();
                        System.out.print("Cat ID: ");
                        int catId = Integer.parseInt(sc.nextLine());

                        lastFeedbackId = feedbackService.createFeedback(currentUser, title, msg, catId);
                        System.out.println("ID: " + lastFeedbackId);
                    }
                }

                case "3" -> {
                    if (isLogged() && lastFeedbackId != null) {
                        System.out.print("Text: ");
                        String text = sc.nextLine();
                        feedbackService.addComment(currentUser, lastFeedbackId, text);
                    }
                }

                case "4" -> {
                    if (isLogged() && lastFeedbackId != null) {
                        FullFeedbackDTO full = feedbackService.getFull(lastFeedbackId, currentUser);
                        System.out.println(full);
                    }
                }

                case "5" -> System.out.println(sController.getAllSomethings());

                case "6" -> {
                    if (isLogged()) {
                        System.out.print("ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.println(sController.deleteSomething(id, currentUser.role()));
                    }
                }

                case "7" -> {
                    currentUser = null;
                    lastFeedbackId = null;
                }

                case "8" -> {
                    return;
                }
            }
        }
    }

    private static boolean isLogged() {
        if (currentUser == null) {
            System.out.println("Login first");
            return false;
        }
        return true;
    }

    private static void printStatus() {
        if (currentUser != null) {
            System.out.println("\nUser: " + currentUser.email() + " [" + currentUser.role() + "]");
        }
    }
}