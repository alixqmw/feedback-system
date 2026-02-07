package models;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private Role role;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(int id, String name, String email, String password, Role role) {
        this(name, password);
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
}
