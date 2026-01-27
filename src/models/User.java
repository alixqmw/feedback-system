package models;

public class User {
    private int id;
    private String name;
    private String password;

    public User() {

    }

    public User(String name, String password) {
        setname(name);
        setPassword(password);
    }

    public User(int id, String name, String password) {
        this(name, password);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'';
    }
}
