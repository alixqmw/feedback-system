package menus;

import controllers.interfaces.ISomethingController;
import models.User;
import repositories.SomethingRepository;
import repositories.interfaces.ISomethingRepository;

import java.util.Scanner;


public class FeedbackMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final User user;
    private final ISomethingController controller;

    public FeedbackMenu(User user, ISomethingController controller) {
        this.user = user;
        this.controller = controller;
    }

    private void menu() {
        System.out.println();
        System.out.println("Welcome, " + user.getname());
        System.out.println("1. Select Something");
        System.out.println("2. Logout");
        System.out.print("Enter option(1-2): ");
    }

    public void start() {
        while (true) {
            menu();
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    moviesMenu();
                    break;
                default:
                    System.out.println("Logged out.");
                    return;
            }
        }
    }
    public void moviesMenu() {
        System.out.println("Something: ");
        String response = controller.getAllSomethings();
        System.out.println(response);
        System.out.println();
        System.out.println("Enter option: ");
        int option = scanner.nextInt();
        System.out.println("Give your Feedback: ");
        String feedback = scanner.next();
        controller.insertFeedback(feedback, option);
        System.out.println("Thank you for your Feedback!");
    }
}
