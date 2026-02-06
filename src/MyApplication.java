import controllers.interfaces.IUserController;
import controllers.interfaces.ISomethingController;
import menus.FeedbackMenu;
import models.User;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IUserController userController;
    private final ISomethingController feedbackController;

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";

    public MyApplication(IUserController controller, ISomethingController feedbackController) {
        this.userController = controller;
        this.feedbackController = feedbackController;
    }

    private void mainMenu() {
        System.out.println();
        System.out.println(YELLOW + "=== FEEDBACK SYSTEM MENU ===" + RESET);
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.print(YELLOW + "> Select option: " + RESET);
    }

    public void start() {
        while (true) {
            mainMenu();
            try {
                int option = scanner.nextInt();
                if (option == 0) break;

                switch (option) {
                    case 1: loginMenu(); break;
                    case 2: registerMenu(); break;
                    default: System.out.println(RED + "Invalid option." + RESET);
                }
            } catch (InputMismatchException e) {
                System.out.println(RED + "Error: Input must be a number." + RESET);
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(RED + e.getMessage() + RESET);
            }
            System.out.println(YELLOW + "-------------------------" + RESET);
        }
    }

    public void registerMenu() {
        System.out.print("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        String response = userController.register(name, password);
        System.out.println(GREEN + response + RESET);
    }

    public void loginMenu() {
        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        User user = userController.login(name, password);

        if (user == null) {
            System.out.println(RED + "Access Denied: Invalid credentials." + RESET);
            return;
        }

        System.out.println(GREEN + "Access Granted! Welcome, " + user.getName() + RESET);

        FeedbackMenu feedbackMenu = new FeedbackMenu(user, feedbackController);
        feedbackMenu.start();
    }
}