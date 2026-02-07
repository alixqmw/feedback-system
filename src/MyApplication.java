import controllers.interfaces.IUserController;
import controllers.interfaces.IFeedbackController;
import menus.FeedbackMenu;
import models.User;

import java.util.Scanner;

public class MyApplication {

    private final Scanner scanner = new Scanner(System.in);

    private final IUserController userController;
    private final IFeedbackController feedbackController;

    public MyApplication(IUserController userController,
                         IFeedbackController feedbackController) {
        this.userController = userController;
        this.feedbackController = feedbackController;
    }

    private void mainMenu() {
        System.out.println();
        System.out.println("Welcome to Feedback System App");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.print("Enter option: ");
    }

    public void start() {
        while (true) {
            mainMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> loginMenu();
                case "2" -> registerMenu();
                case "0" -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }

            System.out.println("*************************");
        }
    }

    private void registerMenu() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String response = userController.register(name, password);
        System.out.println(response);
    }

    private void loginMenu() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userController.login(name, password);

        if (user == null) {
            System.out.println("Login failed. Invalid credentials.");
            return;
        }

        System.out.println("Login successful!");

        FeedbackMenu feedbackMenu = new FeedbackMenu(user, feedbackController);
        feedbackMenu.start();
    }
}
