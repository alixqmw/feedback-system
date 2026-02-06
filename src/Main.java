import controllers.SomethingController;
import controllers.UserController;
import controllers.interfaces.ISomethingController;
import controllers.interfaces.IUserController;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.SomethingRepository;
import repositories.UserRepository;
import repositories.interfaces.ISomethingRepository;
import repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) {
        System.out.println("\u001B[34m" + "****************************************");
        System.out.println("* SYSTEM INITIALIZATION...        *");
        System.out.println("****************************************" + "\u001B[0m");

        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "0000", "feedback_system");
        IUserRepository userRepo = new UserRepository(db);
        ISomethingRepository feedbackRepo = new SomethingRepository(db);
        IUserController userController = new UserController(userRepo);
        ISomethingController feedbackController = new SomethingController(feedbackRepo);
        MyApplication app = new MyApplication(userController, feedbackController);

        app.start();
        db.close();
    }
}