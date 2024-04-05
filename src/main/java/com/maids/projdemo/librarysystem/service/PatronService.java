package com.maids.projdemo.librarysystem.service;

import com.maids.projdemo.librarysystem.entity.BorrowingRecord;
import com.maids.projdemo.librarysystem.entity.Patron;

import java.util.List;
import java.util.Optional;

public interface PatronService {
    List<Patron> getAllPatrons();
    Optional<Patron> getPatronById(Long id);
    Patron addPatron(Patron patron);
    Patron updatePatron(Long id, Patron patron);
    void deletePatron(Long id);
    BorrowingRecord borrowBook(Long bookId, Long patronId);

    BorrowingRecord returnBook(Long bookId, Long patronId);
}
