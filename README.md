**✅ Berikut adalah `README.md` yang profesional dan siap dipakai untuk GitHub.**

### 📁 `README.md`

```markdown
# To-Do List Console Application

Aplikasi manajemen tugas (To-Do List) berbasis console yang dibangun menggunakan **Java** dengan konsep **Object-Oriented Programming (OOP)** yang baik.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![OOP](https://img.shields.io/badge/OOP-Encapsulation%20%7C%20Inheritance%20%7C%20Polymorphism-blue?style=for-the-badge)

---

## ✨ Fitur

- ➕ Menambahkan task baru
- 📋 Melihat semua task
- ✏️ Update task (judul, deskripsi, deadline, prioritas)
- 🗑️ Menghapus task
- ✅ Menandai task sebagai selesai
- 🔍 Pencarian task (berdasarkan judul, prioritas, status)
- 💾 Penyimpanan data permanen menggunakan Serialization
- 📊 Tampilan tabel yang rapi
- 🛡️ Exception handling & validasi input

---

## 🏗️ Struktur Project

```
todo-list-console/
├── src/
│   └── com/
│       └── gian/
│           └── todolist/
│               ├── Main.java
│               ├── model/
│               │   └── Task.java
│               ├── service/
│               │   └── TaskManager.java
│               └── util/
│                   └── FileHandler.java
├── data/
│   └── tasks.ser
├── README.md
└── .gitignore
```

---

## 🛠️ Teknologi yang Digunakan

- **Java** (JDK 8 atau lebih baru)
- **OOP Principles**: Encapsulation, Abstraction, Inheritance, Polymorphism
- **Collection Framework** (`ArrayList`, Stream API)
- **File Handling** (Object Serialization)
- **Clean Code & Modular Architecture**

---

## 🚀 Cara Menjalankan

### 1. Clone Repository
```bash
git clone https://github.com/username/todo-list-console.git
cd todo-list-console
```

### 2. Compile Program
```bash
javac -d . src/com/gian/todolist/*.java src/com/gian/todolist/*/*.java
```

### 3. Jalankan Aplikasi
```bash
java com.gian.todolist.Main
```

---

## 📋 Menu Aplikasi

```
=== TO-DO LIST MENU ===
1. Tambah Task
2. Lihat Semua Task
3. Update Task
4. Hapus Task
5. Tandai Task Selesai
6. Cari Task
7. Keluar
```

---

## 🎯 Tujuan Project

Project ini dibuat untuk melatih:
- Pemahaman konsep OOP di Java
- Modular programming & package structure
- File persistence
- Exception handling
- Clean code architecture

---

## 📌 Cara Compile & Run (Alternatif)

Jika ingin lebih mudah, buat file `run.bat` (Windows) atau `run.sh` (Linux/Mac).

### Contoh `run.bat` (Windows):
```batch
@echo off
javac -d . src/com/gian/todolist/*.java src/com/gian/todolist/*/*.java
java com.gian.todolist.Main
pause
```

---
