package com.gian.todolist.util;

import com.gian.todolist.model.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String DATA_DIR = "data";
    private static final String FILE_PATH = DATA_DIR + "/tasks.ser";

    public void saveTasks(List<Task> tasks) {
        try {
            new File(DATA_DIR).mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
                oos.writeObject(tasks);
            }
            System.out.println("💾 Data berhasil disimpan.");
        } catch (IOException e) {
            System.err.println("❌ Gagal menyimpan data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Task> loadTasks() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<Task>) ois.readObject();
        } catch (Exception e) {
            System.err.println("❌ Gagal memuat data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}