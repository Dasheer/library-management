package com.oura.library.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.oura.library.model.Book;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String USER_NAME = System.getProperty("user.home");
    private static final String APP_DIR_NAME = ".library";
    private static final String FILE_NAME = "book.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static File getfile() {
        File appDir = new File(USER_NAME, APP_DIR_NAME);
        if (!appDir.exists()) appDir.mkdirs();
        return new File(appDir, FILE_NAME);
    }

    public static void saveLibrary(List<Book> books) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            System.err.println("Error saving book: " + e.getMessage());
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                            "Error saving book: " + e.getMessage(),
                            "Error saving",
                            JOptionPane.ERROR_MESSAGE)
            );
        }
    }

    public static List<Book> loadLibrary() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Book>>() {
            }.getType();
            List<Book> loadedBooks = gson.fromJson(reader, listType);
            return loadedBooks != null ? loadedBooks : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                            "Error loading books: " + e.getMessage(),
                            "Error loading",
                            JOptionPane.ERROR_MESSAGE)
            );
            return new ArrayList<>();
        }
    }
}
