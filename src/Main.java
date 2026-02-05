import controllers.SomethingController;
import controllers.UserController;
import controllers.interfaces.ISomethingController;
import controllers.interfaces.IUserController;
import data.PostgresDB;
import data.interfaces.IDB;
import models.Something;
import repositories.SomethingRepository;
import repositories.UserRepository;
import repositories.interfaces.ISomethingRepository;
import repositories.interfaces.IUserRepository;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        IDB db = new PostgresDB(
                "jdbc:postgresql://localhost:5432",
                "postgres",
                "0000",
                "feedback_system"
        );

        IUserRepository userRepo = new UserRepository(db);
        ISomethingRepository somethingRepo = new SomethingRepository(db) {
            @Override
            public List<Something> getSomethingsByCategory(int categoryId) {
                return List.of();
            }

            @Override
            public boolean deleteSomething(int id, String userRole) {
                return false;
            }
        };

        IUserController userController = new UserController(userRepo);
        ISomethingController somethingController = new SomethingController(somethingRepo);

        MyApplication app = new MyApplication(userController, somethingController);
        app.start();

        db.close();
    }
}
