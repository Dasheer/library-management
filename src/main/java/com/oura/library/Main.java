package com.oura.library;

import com.oura.library.data.FileManager;
import com.oura.library.gui.LibraryWindow;
import com.oura.library.model.Book;
import com.oura.library.model.LibraryManager;

import javax.swing.*;
import java.util.List;

public class Main {
    static void main(String[] args) {
        List<Book> savedBooks = FileManager.loadLibrary();

        LibraryManager manager = new LibraryManager();
        for (Book b : savedBooks) {
            manager.addBook(b.getTitle(), b.getAuthor(), b.getIsbn());
            if (!b.isAvailable()) {
                manager.borrowBook(b.getIsbn());
            }
        }

        SwingUtilities.invokeLater(() -> new LibraryWindow(manager));
    }
}
