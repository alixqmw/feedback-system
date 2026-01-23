package com.company.models;

public class Feedback {
    private int id;
    private String name;
    private int rating;
    private String comment;

    public Feedback() {}

    public Feedback(String name, int rating, String comment) {
        this.name = name;
        this.rating = rating;
        this.comment = comment;
    }

    public Feedback(int id, String name, int rating, String comment) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return "Feedback{id=" + id + ", name='" + name + "', rating=" + rating + ", comment='" + comment + "'}";
    }
}