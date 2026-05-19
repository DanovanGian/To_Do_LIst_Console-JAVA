package com.gian.todolist.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String description;
    private String deadline;         // yyyy-MM-dd
    private Priority priority;
    private Category category;       // ← Baru
    private boolean isDone;

    public enum Priority { LOW, MEDIUM, HIGH }
    public enum Category { 
        KERJA, KULIAH, PRIBADI, OLAHRAGA, BELANJA, LAINNYA 
    }

    public Task(int id, String title, String description, String deadline, 
                Priority priority, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
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
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public boolean isDone() { return isDone; }

    public void markAsDone() { this.isDone = true; }

    public void update(String title, String description, String deadline, 
                      Priority priority, Category category) {
        if (title != null && !title.trim().isEmpty()) this.title = title;
        if (description != null) this.description = description;
        if (deadline != null && !deadline.trim().isEmpty()) this.deadline = deadline;
        if (priority != null) this.priority = priority;
        if (category != null) this.category = category;
    }

    public String toTableRow() {
        String status = isDone ? "✅ DONE" : "⏳ PENDING";
        String shortTitle = title.length() > 22 ? title.substring(0, 19) + "..." : title;
        return String.format("| %-3d | %-22s | %-8s | %-12s | %-7s | %-8s |", 
                id, shortTitle, status, deadline, priority, category);
    }

    // Untuk reminder
    public boolean isNearDeadline() {
        try {
            LocalDate due = LocalDate.parse(deadline);
            LocalDate today = LocalDate.now();
            return !isDone && (due.isBefore(today.plusDays(4)) && !due.isBefore(today));
        } catch (Exception e) {
            return false;
        }
    }
}