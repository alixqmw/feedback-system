package com.company;

import com.company.controllers.interfaces.IFeedbackController;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IFeedbackController controller;

    public MyApplication(IFeedbackController controller) {
        this.controller = controller;
    }

    public void start() {
        while (true) {
            System.out.println("\n1. All users\n2. User by id\n3. Create user\n4. All reviews\n5. Create review\n0. Exit");
            try {
                int opt = scanner.nextInt();
                if (opt == 0) break;
                switch (opt) {
                    case 1 -> System.out.println(controller.getAllUsers());
                    case 2 -> {
                        System.out.print("ID: ");
                        System.out.println(controller.getUser(scanner.nextInt()));
                    }
                    case 3 -> {
                        System.out.print("Name: ");
                        System.out.println(controller.createUser(scanner.next()));
                    }
                    case 4 -> System.out.println(controller.getAllReviews());
                    case 5 -> {
                        System.out.print("Name: "); String n = scanner.next();
                        System.out.print("Rating: "); int r = scanner.nextInt();
                        System.out.print("Comment: "); scanner.nextLine();
                        System.out.println(controller.createReview(n, r, scanner.nextLine()));
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}