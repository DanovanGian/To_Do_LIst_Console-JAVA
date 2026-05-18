package com.gian.todolist.model;

import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String description;
    private String deadline;         // format: yyyy-MM-dd
    private Priority priority;
    private boolean isDone;

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    public Task(int id, String title, String description, String deadline, Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.isDone = false;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public boolean isDone() { return isDone; }

    public void markAsDone() {
        this.isDone = true;
    }

    public void update(String title, String description, String deadline, Priority priority) {
        if (title != null && !title.trim().isEmpty()) this.title = title;
        if (description != null) this.description = description;
        if (deadline != null && !deadline.trim().isEmpty()) this.deadline = deadline;
        if (priority != null) this.priority = priority;
    }

    public String toTableRow() {
        String status = isDone ? "✅ DONE" : "⏳ PENDING";
        String shortTitle = title.length() > 25 ? title.substring(0, 22) + "..." : title;
        return String.format("| %-3d | %-25s | %-10s | %-12s | %-7s |", 
                id, shortTitle, status, deadline, priority);
    }
}