package com.gian.todolist.service;

import com.gian.todolist.model.Task;
import com.gian.todolist.model.Task.Category;
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

    public void addTask(String title, String description, String deadline, 
                       Task.Priority priority, Task.Category category) {
        Task task = new Task(nextId++, title, description, deadline, priority, category);
        tasks.add(task);
        fileHandler.saveTasks(tasks);
        System.out.println("✅ Task berhasil ditambahkan (ID: " + task.getId() + ")");
    }

    public void showAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("📭 Belum ada task.");
            return;
        }

        System.out.println("\n" + "=".repeat(85));
        System.out.printf("| %-3s | %-22s | %-8s | %-12s | %-7s | %-8s |\n", 
                "ID", "JUDUL", "STATUS", "DEADLINE", "PRIORITAS", "KATEGORI");
        System.out.println("=".repeat(85));
        tasks.forEach(t -> System.out.println(t.toTableRow()));
        System.out.println("=".repeat(85));
    }

    // === FITUR BARU ===
    public void sortByDeadline() {
        tasks.sort(Comparator.comparing(Task::getDeadline));
        System.out.println("✅ Task diurutkan berdasarkan Deadline.");
    }

    public void sortByPriority() {
        tasks.sort(Comparator.comparing(Task::getPriority).reversed());
        System.out.println("✅ Task diurutkan berdasarkan Prioritas (High → Low).");
    }

    public void showReminder() {
        List<Task> near = tasks.stream()
                .filter(Task::isNearDeadline)
                .collect(Collectors.toList());

        if (near.isEmpty()) {
            System.out.println("🎉 Tidak ada task yang mendekati deadline!");
            return;
        }

        System.out.println("\n⚠️  REMINDER - Task Mendekati Deadline (≤ 3 hari):");
        System.out.println("=".repeat(60));
        near.forEach(t -> System.out.println(t.toTableRow()));
        System.out.println("=".repeat(60));
    }

    // Method lama tetap sama (update, delete, markAsDone, search)
    // ... (saya singkat di sini, tapi tetap pakai kode sebelumnya)
    public boolean updateTask(int id, String title, String description, String deadline, 
                             Task.Priority priority, Task.Category category) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                t.update(title, description, deadline, priority, category);
                fileHandler.saveTasks(tasks);
                System.out.println("✅ Task ID " + id + " berhasil diupdate.");
                return true;
            }
        }
        System.out.println("❌ Task ID " + id + " tidak ditemukan.");
        return false;
    }

    public boolean deleteTask(int id) {
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) fileHandler.saveTasks(tasks);
        return removed;
    }

    public boolean markAsDone(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                if (!t.isDone()) {
                    t.markAsDone();
                    fileHandler.saveTasks(tasks);
                    System.out.println("✅ Task ID " + id + " ditandai selesai.");
                }
                return true;
            }
        }
        return false;
    }
        // Tambahkan atau ganti dengan method ini
    public void searchTasks(String keyword, Task.Priority priority, Boolean isDone) {
        List<Task> result = tasks.stream()
                .filter(t -> keyword == null || 
                        t.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .filter(t -> priority == null || t.getPriority() == priority)
                .filter(t -> isDone == null || t.isDone() == isDone)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("🔍 Tidak ada task yang cocok dengan pencarian.");
            return;
        }

        System.out.println("\n🔍 HASIL PENCARIAN (" + result.size() + " ditemukan):");
        System.out.println("=".repeat(85));
        System.out.printf("| %-3s | %-22s | %-8s | %-12s | %-7s | %-8s |\n", 
                "ID", "JUDUL", "STATUS", "DEADLINE", "PRIORITAS", "KATEGORI");
        System.out.println("=".repeat(85));
        
        result.forEach(t -> System.out.println(t.toTableRow()));
        System.out.println("=".repeat(85));
    }
}