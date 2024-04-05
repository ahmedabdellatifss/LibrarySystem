package com.maids.projdemo.librarysystem;

import com.maids.projdemo.librarysystem.controller.BookController;
import com.maids.projdemo.librarysystem.entity.Book;
import com.maids.projdemo.librarysystem.service.BookServiceImp;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class BookControllerTest {
    @Mock
    private BookServiceImp bookService;

    @InjectMocks
    private BookController bookController;

    public BookControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        // Prepare test data
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", "Author 1", 2021, "ISBN 1", true));
        books.add(new Book(2L, "Book 2", "Author 2", 2022, "ISBN 2", true));

        // Mock the behavior of the BookService
        when(bookService.getAllBooks()).thenReturn(books);

        // Invoke the controller method
        ResponseEntity<List<Book>> responseEntity = bookController.getAllBooks();

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }

    @Test
    public void testGetBookById() {
        // Prepare test data
        Long bookId = 1L;
        Book book = new Book(bookId, "Book 1", "Author 1", 2021, "ISBN 1", true);

        // Mock the behavior of the BookService
        when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));

        // Invoke the controller method
        ResponseEntity<Book> responseEntity = bookController.getBookById(bookId);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }
    @Test
    public void testAddBook() {
        // Prepare test data
        Book book = new Book(1L, "Book 1", "Author 1", 2021, "ISBN 1", true);

        // Mock the behavior of the BookService
        when(bookService.addBook(book)).thenReturn(book);

        // Invoke the controller method
        ResponseEntity<Book> responseEntity = bookController.addBook(book);

        // Verify the response
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }

    @Test
    public void testUpdateBook() {
        // Prepare test data
        Long bookId = 1L;
        Book updatedBook = new Book(bookId, "Updated Book", "Updated Author", 2022, "ISBN 2", true);

        // Mock the behavior of the BookService
        when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(updatedBook);

        // Invoke the controller method
        ResponseEntity<Book> responseEntity = bookController.updateBook(bookId, updatedBook);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedBook, responseEntity.getBody());
    }

    @Test
    public void testDeleteBookById() {
        // Prepare test data
        Long bookId = 1L;

        // Invoke the controller method
        ResponseEntity<Void> responseEntity = bookController.deleteBook(bookId);

        // Verify the response
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(bookService, times(1)).deleteBook(bookId);
    }


}
