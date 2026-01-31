

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

    public MyApplication(IUserController controller, ISomethingController feedbackController) {
        this.userController = controller;
        this.feedbackController = feedbackController;
    }

    private void mainMenu() {
        System.out.println();
        System.out.println("Welcome to Feedback system app");
        System.out.println("1. Login: ");
        System.out.println("2. Register: ");
        /*System.out.println("2. Get user by id");
        System.out.println("3. Create user");*/
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Enter option (1-2): ");
    }

    public void start() {
        while (true) {
            mainMenu();
            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1: loginMenu(); break;
                    case 2: registerMenu(); break;
                    default: return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be integer: " + e);
                scanner.nextLine(); // to ignore incorrect input
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("*************************");
        }
    }

    public void getAllUsersMenu() {
        String response = userController.getAllUsers();
        System.out.println(response);
    }

    public void getUserByIdMenu() {
        System.out.println("Please enter id");

        int id = scanner.nextInt();

        String response = userController.getUser(id);
        System.out.println(response);
    }

    public void registerMenu() {
        System.out.println("Please enter name");
        String name = scanner.next();
        System.out.println("Please enter password");
        String password = scanner.next();

        String response = userController.register(name, password);
        System.out.println(response);
    }

    public void loginMenu() {
        System.out.println("Please enter name:");
        String name = scanner.next();

        System.out.println("Please enter password:");
        String password = scanner.next();

        User user = userController.login(name, password);

        if (user == null) {
            System.out.println("Login failed. User not found.");
            return;
        }

        System.out.println("Login successful!");

        FeedbackMenu feedbackMenu = new FeedbackMenu(user, feedbackController);
        feedbackMenu.start();
    }
}
