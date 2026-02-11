package models;

public class Something {
    private int id;
    private String name;
    private String feedback;

    public Something(String name,  String feedback) {
        this.name = name;
        this.feedback = feedback;
    }

    public Something(int id, String name, String feedback) {
        this(name, feedback);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return id + " " + name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}