package org.example;

public interface ExternalBookService {

    boolean isBookAvailable(String title);

    Book fetchBookDetails(String title);
}