package com.gian.todolist.service;

import com.gian.todolist.model.Task;
import com.gian.todolist.util.FileHandler;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {
    private final List<Task> tasks;
    private final FileHandler fileHandler;
    private int nextId;

    public TaskManager() {
        this.fileHandler = new FileHandler();
        this.tasks = fileHandler.loadTasks();
        this.nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
    }

    public void addTask(String title, String description, String deadline, Task.Priority priority) {
        Task task = new Task(nextId++, title, description, deadline, priority);
        tasks.add(task);
        fileHandler.saveTasks(tasks);
        System.out.println("✅ Task berhasil ditambahkan (ID: " + task.getId() + ")");
    }

    public void showAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("📭 Belum ada task.");
            return;
        }

        System.out.println("\n" + "=".repeat(72));
        System.out.printf("| %-3s | %-25s | %-10s | %-12s | %-7s |\n", 
                "ID", "JUDUL", "STATUS", "DEADLINE", "PRIORITAS");
        System.out.println("=".repeat(72));

        tasks.forEach(task -> System.out.println(task.toTableRow()));
        System.out.println("=".repeat(72));
    }

    public boolean updateTask(int id, String title, String description, String deadline, Task.Priority priority) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                t.update(title, description, deadline, priority);
                fileHandler.saveTasks(tasks);
                System.out.println("✅ Task ID " + id + " berhasil diupdate.");
                return true;
            }
        }
        System.out.println("❌ Task dengan ID " + id + " tidak ditemukan.");
        return false;
    }

    public boolean deleteTask(int id) {
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            fileHandler.saveTasks(tasks);
            System.out.println("🗑️ Task ID " + id + " berhasil dihapus.");
        } else {
            System.out.println("❌ Task ID " + id + " tidak ditemukan.");
        }
        return removed;
    }

    public boolean markAsDone(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                if (t.isDone()) {
                    System.out.println("⚠️ Task ini sudah selesai.");
                } else {
                    t.markAsDone();
                    fileHandler.saveTasks(tasks);
                    System.out.println("✅ Task ID " + id + " ditandai selesai.");
                }
                return true;
            }
        }
        System.out.println("❌ Task tidak ditemukan.");
        return false;
    }

    public void searchTasks(String keyword, Task.Priority priority, Boolean isDone) {
        List<Task> result = tasks.stream()
                .filter(t -> keyword == null || t.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .filter(t -> priority == null || t.getPriority() == priority)
                .filter(t -> isDone == null || t.isDone() == isDone)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("🔍 Tidak ada task yang cocok.");
            return;
        }

        System.out.println("\n🔍 Hasil Pencarian (" + result.size() + " ditemukan):");
        result.forEach(t -> System.out.println(t.toTableRow()));
    }
}