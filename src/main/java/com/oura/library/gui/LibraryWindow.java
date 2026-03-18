package com.oura.library.gui;

import com.oura.library.data.FileManager;
import com.oura.library.model.LibraryManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LibraryWindow extends JFrame {
    private LibraryManager manager;

    public LibraryWindow(LibraryManager manager) {
        this.manager = manager;

        setTitle("Oura - Library Management System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LibraryPanel panel = new LibraryPanel(manager);
        this.add(panel);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FileManager.saveLibrary(manager.getBooks());
                System.exit(0);
            }
        });
        setVisible(true);
    }
}
