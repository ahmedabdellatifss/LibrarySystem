package com.maids.projdemo.librarysystem.service;

import com.maids.projdemo.librarysystem.entity.BorrowingRecord;

import java.util.List;
import java.util.Optional;

public interface BorrowingService {
    List<BorrowingRecord> getAllBorrowings();
    Optional<BorrowingRecord> getBorrowingById(Long id);
    BorrowingRecord borrowBook(Long bookId, Long patronId);
    BorrowingRecord returnBook(Long bookId, Long patronId);
}
