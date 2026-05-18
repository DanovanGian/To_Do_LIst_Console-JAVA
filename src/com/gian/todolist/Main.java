package com.gian.todolist;

import com.gian.todolist.model.Task;
import com.gian.todolist.service.TaskManager;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        System.out.println("🚀 Welcome to To-Do List Console App");
        System.out.println("=".repeat(55));

        while (true) {
            printMenu();
            int choice = getValidInteger("Pilih menu (1-7): ");

            switch (choice) {
                case 1 -> addTask();
                case 2 -> taskManager.showAllTasks();
                case 3 -> updateTask();
                case 4 -> deleteTask();
                case 5 -> markAsDone();
                case 6 -> searchTask();
                case 7 -> {
                    System.out.println("👋 Terima kasih telah menggunakan aplikasi!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("❌ Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           TO-DO LIST MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Tambah Task");
        System.out.println("2. Lihat Semua Task");
        System.out.println("3. Update Task");
        System.out.println("4. Hapus Task");
        System.out.println("5. Tandai Task Selesai");
        System.out.println("6. Cari Task");
        System.out.println("7. Keluar");
        System.out.println("=".repeat(40));
    }

    private static void addTask() {
        System.out.print("Judul Task       : ");
        String title = scanner.nextLine();

        System.out.print("Deskripsi        : ");
        String description = scanner.nextLine();

        String deadline;
        while (true) {
            System.out.print("Deadline (yyyy-MM-dd): ");
            deadline = scanner.nextLine();
            try {
                LocalDate.parse(deadline);
                break;
            } catch (Exception e) {
                System.out.println("❌ Format tanggal salah! Gunakan yyyy-MM-dd");
            }
        }

        System.out.println("Prioritas: 1. LOW | 2. MEDIUM | 3. HIGH");
        int prio = getValidInteger("Pilih prioritas: ");
        Task.Priority priority = switch (prio) {
            case 1 -> Task.Priority.LOW;
            case 3 -> Task.Priority.HIGH;
            default -> Task.Priority.MEDIUM;
        };

        taskManager.addTask(title, description, deadline, priority);
    }

    private static void updateTask() {
        int id = getValidInteger("Masukkan ID Task yang ingin diupdate: ");
        System.out.print("Judul baru (kosong jika tidak diubah): ");
        String title = scanner.nextLine();
        System.out.print("Deskripsi baru: ");
        String desc = scanner.nextLine();
        System.out.print("Deadline baru (yyyy-MM-dd): ");
        String deadline = scanner.nextLine();

        System.out.println("Prioritas: 1.LOW 2.MEDIUM 3.HIGH (0 = tidak ubah)");
        int p = getValidInteger("Pilih: ");
        Task.Priority priority = (p == 0) ? null : 
                (p == 1 ? Task.Priority.LOW : p == 3 ? Task.Priority.HIGH : Task.Priority.MEDIUM);

        taskManager.updateTask(id, title.isBlank() ? null : title, desc, 
                              deadline.isBlank() ? null : deadline, priority);
    }

    private static void deleteTask() {
        int id = getValidInteger("Masukkan ID Task yang ingin dihapus: ");
        taskManager.deleteTask(id);
    }

    private static void markAsDone() {
        int id = getValidInteger("Masukkan ID Task yang selesai: ");
        taskManager.markAsDone(id);
    }

    private static void searchTask() {
        System.out.print("Keyword judul (kosong = semua): ");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) keyword = null;

        System.out.println("Prioritas: 1.LOW 2.MEDIUM 3.HIGH (0 = semua)");
        int p = getValidInteger("Pilih: ");
        Task.Priority priority = (p == 0) ? null : 
                (p == 1 ? Task.Priority.LOW : p == 3 ? Task.Priority.HIGH : Task.Priority.MEDIUM);

        System.out.println("Status: 1. Pending | 2. Done | 0 = Semua");
        int s = getValidInteger("Pilih: ");
        Boolean status = (s == 0) ? null : (s == 2);

        taskManager.searchTasks(keyword, priority, status);
    }

    private static int getValidInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Masukkan angka yang valid!");
            }
        }
    }
}