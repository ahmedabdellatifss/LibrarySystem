package com.maids.projdemo.librarysystem;
import com.maids.projdemo.librarysystem.controller.PatronController;
import com.maids.projdemo.librarysystem.entity.Patron;
import com.maids.projdemo.librarysystem.service.PatronServiceImp;
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
public class PatronControllerTest {
    @Mock
    private PatronServiceImp patronService;

    @InjectMocks
    private PatronController patronController;

    public PatronControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPatrons() {
        // Prepare test data
        List<Patron> patrons = new ArrayList<>();
        patrons.add(new Patron(1L, "John Doe", "john@example.com", "1234567890"));
        patrons.add(new Patron(2L, "Jane Smith", "jane@example.com", "0987654321"));

        // Mock the behavior of the PatronService
        when(patronService.getAllPatrons()).thenReturn(patrons);

        // Invoke the controller method
        ResponseEntity<List<Patron>> responseEntity = patronController.getAllPatrons();

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(patrons, responseEntity.getBody());
    }

    @Test
    public void testGetPatronById() {
        // Prepare test data
        Long patronId = 1L;
        Patron patron = new Patron(patronId, "John Doe", "john@example.com", "1234567890");

        // Mock the behavior of the PatronService
        when(patronService.getPatronById(patronId)).thenReturn(Optional.of(patron));

        // Invoke the controller method
        ResponseEntity<Patron> responseEntity = patronController.getPatronById(patronId);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(patron, responseEntity.getBody());
    }
    @Test
    public void testAddPatron() {
        // Prepare test data
        Patron patron = new Patron(1L, "John Doe", "john@example.com", "1234567890");

        // Mock the behavior of the PatronService
        when(patronService.addPatron(patron)).thenReturn(patron);

        // Invoke the controller method
        ResponseEntity<Patron> responseEntity = patronController.addPatron(patron);

        // Verify the response
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(patron, responseEntity.getBody());
    }

    @Test
    public void testUpdatePatron() {
        // Prepare test data
        Long patronId = 1L;
        Patron updatedPatron = new Patron(patronId, "Updated Name", "updated@example.com", "9876543210");

        // Mock the behavior of the PatronService
        when(patronService.updatePatron(eq(patronId), any(Patron.class))).thenReturn(updatedPatron);

        // Invoke the controller method
        ResponseEntity<Patron> responseEntity = patronController.updatePatron(patronId, updatedPatron);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedPatron, responseEntity.getBody());
    }

    @Test
    public void testDeletePatronById() {
        // Prepare test data
        Long patronId = 1L;

        // Invoke the controller method
        ResponseEntity<Void> responseEntity = patronController.deletePatron(patronId);

        // Verify the response
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(patronService, times(1)).deletePatron(patronId);
    }
}
