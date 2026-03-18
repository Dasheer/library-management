package com.oura.library;

import com.oura.library.data.FileManager;
import com.oura.library.gui.LibraryWindow;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.oura.library.model.Book;
import com.oura.library.model.LibraryManager;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatDarculaLaf());
            } catch (Exception e) {
                System.err.println("Failed to set FlatLaf look and feel");
            }

            List<Book> savedBooks = FileManager.loadLibrary();
            LibraryManager manager = new LibraryManager();
            for (Book b : savedBooks) {
                manager.addBook(b.getTitle(), b.getAuthor(), b.getIsbn());
                if (!b.isAvailable()) {
                    manager.borrowBook(b.getIsbn());
                }
            }

            new LibraryWindow(manager);
        });
    }
}
