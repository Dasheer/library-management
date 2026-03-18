package com.oura.library.model;

import java.util.ArrayList;
import java.util.List;

public class LibraryManager {

    private List<Book> books;

    public LibraryManager() {
        this.books = new ArrayList<>();
    }

    public void addBook(String title, String author, String isbn) {
        Book newBook = new Book(title, author, isbn);
        books.add(newBook);
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public boolean borrowBook(String isbn) {
        Book book = findBookByIsbn(isbn);

        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            return true;
        }
        return false;
    }

    public boolean returnBook(String isbn) {
        Book book = findBookByIsbn(isbn);

        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            return true;
        }
        return false;
    }
}
