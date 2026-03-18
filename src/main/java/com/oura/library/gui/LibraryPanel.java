package com.oura.library.gui;

import com.oura.library.model.Book;
import com.oura.library.model.LibraryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibraryPanel extends JPanel {
    private LibraryManager manager;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField titleField, authorField, isbnField;

    public LibraryPanel(LibraryManager manager) {
        this.manager = manager;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel(new GridLayout(2, 4, 5, 5));

        topPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        topPanel.add(titleField);

        topPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        topPanel.add(authorField);

        topPanel.add(new JLabel("ISBN (Code):"));
        isbnField = new JTextField();
        topPanel.add(isbnField);

        JButton addButton = new JButton("Add Book");
        addButton.setBackground(new Color(46, 139, 87)); // Verde
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> addBook());
        topPanel.add(addButton);

        this.add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Title", "Author", "ISBN", "State"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton borrowButton = new JButton("Selected Lend");
        borrowButton.addActionListener(e -> lendSelectedBook());

        JButton returnButton = new JButton("Return Selected");
        returnButton.addActionListener(e -> returnSelectedBook());

        bottomPanel.add(borrowButton);
        bottomPanel.add(returnButton);

        this.add(bottomPanel, BorderLayout.SOUTH);

        updateTable();
    }

    private void addBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn = isbnField.getText().trim();

        if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
            manager.addBook(title, author, isbn);
            titleField.setText("");
            authorField.setText("");
            isbnField.setText("");
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void lendSelectedBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String isbn = (String) tableModel.getValueAt(selectedRow, 2);
            if (manager.borrowBook(isbn)) {
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Book is already available.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void returnSelectedBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String isbn = (String) tableModel.getValueAt(selectedRow, 2);
            if (manager.returnBook(isbn)) {
                updateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Book is already in the library.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);

        for (Book book : manager.getBooks()) {
            String state = book.isAvailable() ? "Available" : "Borrowed";
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getIsbn(), state});
        }
    }
}
