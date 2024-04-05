package com.maids.projdemo.librarysystem;

import com.maids.projdemo.librarysystem.controller.BorrowingController;
import com.maids.projdemo.librarysystem.entity.BorrowingRecord;
import com.maids.projdemo.librarysystem.service.BorrowingServiceImp;
import com.maids.projdemo.librarysystem.service.PatronServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class BorrowingControllerTest {

    @Mock
    private PatronServiceImp patronService;
    @Mock
    private BorrowingServiceImp borrowingService;

    @InjectMocks
    private BorrowingController borrowingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBorrowBook() {
        // Prepare test data
        Long bookId = 1L;
        Long patronId = 1L;
        BorrowingRecord borrowingRecord = new BorrowingRecord(bookId, patronId);

        // Mock the behavior of the PatronService
        when(patronService.borrowBook(bookId, patronId)).thenReturn(borrowingRecord);

        // Invoke the controller method
        ResponseEntity<BorrowingRecord> responseEntity = borrowingController.borrowBook(bookId, patronId);

        // Verify the response
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(borrowingRecord, responseEntity.getBody());
    }

    @Test
    public void testReturnBook() {
        // Prepare test data
        Long bookId = 1L;
        Long patronId = 1L;

        // Invoke the controller method
        ResponseEntity<Void> responseEntity = borrowingController.returnBook(bookId, patronId);

        // Verify the response
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        // Verify that borrowingService.returnBook is called with correct parameters
        verify(borrowingService, times(1)).returnBook(bookId, patronId);
    }

}
