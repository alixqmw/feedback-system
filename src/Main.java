import data.PostgresDB;

public class Main {
    public static void main(String[] args) {

        PostgresDB db = new PostgresDB();

        try {
            db.getConnection();
            System.out.println("PostgreSQL connection SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
