package com.gian.todolist;

import com.gian.todolist.model.Task;
import com.gian.todolist.service.TaskManager;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        System.out.println("🚀 To-Do List Console Application");
        System.out.println("=".repeat(60));

        while (true) {
            printMainMenu();
            int choice = getValidInteger("Pilih menu (1-9): ");

            switch (choice) {
                case 1 -> addTask();
                case 2 -> taskManager.showAllTasks();
                case 3 -> updateTask();
                case 4 -> deleteTask();
                case 5 -> markAsDone();
                case 6 -> searchTask();
                case 7 -> taskManager.showReminder();
                case 8 -> sortingMenu();
                case 9 -> {
                    System.out.println("👋 Terima kasih telah menggunakan aplikasi!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("❌ Pilihan tidak valid!");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("              MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Tambah Task");
        System.out.println("2. Lihat Semua Task");
        System.out.println("3. Update Task");
        System.out.println("4. Hapus Task");
        System.out.println("5. Tandai Task Selesai");
        System.out.println("6. Cari Task");
        System.out.println("7. Tampilkan Reminder");
        System.out.println("8. Sorting Task");
        System.out.println("9. Keluar");
        System.out.println("=".repeat(50));
    }

    private static void addTask() {
        System.out.print("Judul Task       : ");
        String title = scanner.nextLine();

        System.out.print("Deskripsi        : ");
        String description = scanner.nextLine();

        String deadline;
        while (true) {
            System.out.print("Deadline (yyyy-MM-dd): ");
            deadline = scanner.nextLine().trim();
            try {
                LocalDate.parse(deadline);
                break;
            } catch (Exception e) {
                System.out.println("❌ Format tanggal salah! Gunakan yyyy-MM-dd");
            }
        }

        // Pilih Prioritas
        System.out.println("Prioritas: 1. LOW | 2. MEDIUM | 3. HIGH");
        int prioInput = getValidInteger("Pilih prioritas: ");
        Task.Priority priority = switch (prioInput) {
            case 1 -> Task.Priority.LOW;
            case 3 -> Task.Priority.HIGH;
            default -> Task.Priority.MEDIUM;
        };

        // Pilih Kategori
        System.out.println("Kategori:");
        System.out.println("1. KERJA  2. KULIAH  3. PRIBADI  4. OLAHRAGA  5. BELANJA  6. LAINNYA");
        int catInput = getValidInteger("Pilih kategori: ");
        Task.Category category = switch (catInput) {
            case 1 -> Task.Category.KERJA;
            case 2 -> Task.Category.KULIAH;
            case 4 -> Task.Category.OLAHRAGA;
            case 5 -> Task.Category.BELANJA;
            case 6 -> Task.Category.LAINNYA;
            default -> Task.Category.PRIBADI;
        };

        taskManager.addTask(title, description, deadline, priority, category);
    }

    private static void updateTask() {
        int id = getValidInteger("Masukkan ID Task: ");
        
        System.out.print("Judul baru (kosong = tidak ubah): ");
        String title = scanner.nextLine();
        
        System.out.print("Deskripsi baru: ");
        String desc = scanner.nextLine();
        
        System.out.print("Deadline baru (yyyy-MM-dd): ");
        String deadline = scanner.nextLine();

        System.out.println("Prioritas (1.LOW 2.MEDIUM 3.HIGH, 0=tidak ubah): ");
        int p = getValidInteger("");
        Task.Priority priority = (p == 0) ? null : 
                (p == 1 ? Task.Priority.LOW : p == 3 ? Task.Priority.HIGH : Task.Priority.MEDIUM);

        System.out.println("Kategori (1-6, 0=tidak ubah): ");
        int c = getValidInteger("");
        Task.Category category = (c == 0) ? null : switch (c) {
            case 1 -> Task.Category.KERJA;
            case 2 -> Task.Category.KULIAH;
            case 4 -> Task.Category.OLAHRAGA;
            case 5 -> Task.Category.BELANJA;
            case 6 -> Task.Category.LAINNYA;
            default -> Task.Category.PRIBADI;
        };

        taskManager.updateTask(id, title.isBlank() ? null : title, desc, 
                              deadline.isBlank() ? null : deadline, priority, category);
    }

    private static void deleteTask() {
        int id = getValidInteger("Masukkan ID Task yang akan dihapus: ");
        if (taskManager.deleteTask(id)) {
            System.out.println("🗑️ Task berhasil dihapus.");
        } else {
            System.out.println("❌ Task tidak ditemukan.");
        }
    }

    private static void markAsDone() {
        int id = getValidInteger("Masukkan ID Task yang selesai: ");
        taskManager.markAsDone(id);
    }

    private static void searchTask() {
        System.out.print("Keyword judul (kosong = semua): ");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) keyword = null;

        System.out.print("Prioritas (1.LOW 2.MEDIUM 3.HIGH, 0=semua): ");
        int p = getValidInteger("");
        Task.Priority priority = (p == 0) ? null : 
                (p == 1 ? Task.Priority.LOW : p == 3 ? Task.Priority.HIGH : Task.Priority.MEDIUM);

        System.out.print("Status (1.Pending 2.Done 0=Semua): ");
        int s = getValidInteger("");
        Boolean status = (s == 0) ? null : (s == 2);

        taskManager.searchTasks(keyword, priority, status);
    }

    private static void sortingMenu() {
        System.out.println("\n=== SORTING MENU ===");
        System.out.println("1. Urutkan berdasarkan Deadline");
        System.out.println("2. Urutkan berdasarkan Prioritas");
        System.out.println("3. Kembali ke Menu Utama");

        int choice = getValidInteger("Pilih: ");
        if (choice == 1) {
            taskManager.sortByDeadline();
        } else if (choice == 2) {
            taskManager.sortByPriority();
        }
    }

    private static int getValidInteger(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty() && prompt.contains("0=tidak")) {
                    return 0;
                }
                return Integer.parseInt(input);
            } catch (Exception e) {
                System.out.print("❌ Masukkan angka yang valid: ");
            }
        }
    }
}