import data.PostgresDB;
import dto.FullFeedbackDTO;
import dto.UserDTO;
import repositories.FeedbackRepository;
import repositories.UserRepository;
import services.AuthService;
import services.FeedbackService;

public class Main {
    public static void main(String[] args) {
        PostgresDB db = PostgresDB.getInstance(
                "jdbc:postgresql://localhost:5432/feedback_db",
                "postgres",
                "postgres"
        );

        UserRepository userRepo = new UserRepository(db);
        FeedbackRepository feedbackRepo = new FeedbackRepository(db);

        AuthService auth = new AuthService(userRepo);
        FeedbackService feedbackService = new FeedbackService(feedbackRepo);

        // login as normal user
        UserDTO user = auth.login("user1@mail.com", "1234");
        System.out.println("Logged in: " + user);

        // create feedback
        int newId = feedbackService.createFeedback(user, "Login problem", "I cannot login sometimes", 1);
        System.out.println("Created feedback id: " + newId);

        // add comment
        boolean c1 = feedbackService.addComment(user, newId, "Please fix ASAP");
        System.out.println("Comment added: " + c1);

        // get full feedback (JOIN)
        FullFeedbackDTO full = feedbackService.getFull(newId, currentUser);
        System.out.println("\nFULL FEEDBACK:");
        System.out.println(full);

        // try change status as USER (should fail)
        boolean statusUser = feedbackService.changeStatus(user, newId, "DONE");
        System.out.println("\nUSER change status result: " + statusUser);

        // login as admin
        UserDTO admin = auth.login("admin@mail.com", "1234");
        System.out.println("\nLogged in: " + admin);

        // change status as ADMIN (should work)
        boolean statusAdmin = feedbackService.changeStatus(admin, newId, "IN_PROGRESS");
        System.out.println("ADMIN change status result: " + statusAdmin);

        // reload full
        FullFeedbackDTO full2 = feedbackService.getFull(newId, currentUser);
        System.out.println("\nUPDATED FULL FEEDBACK:");
        System.out.println(full2);

        // lambda example
        System.out.println("\nManager comments count: " + feedbackService.countManagerComments(full2));
    }
}

