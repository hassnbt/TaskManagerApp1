package com.example.taskmanager.models;

public class Notification {
    private int id;
    private String message;
    private String dateTime;

    public Notification() {
    }

    public Notification(String message, String dateTime) {
        this.message = message;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
} 