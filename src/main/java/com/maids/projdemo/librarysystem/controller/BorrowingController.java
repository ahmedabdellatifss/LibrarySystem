package com.maids.projdemo.librarysystem.controller;

import com.maids.projdemo.librarysystem.entity.BorrowingRecord;
import com.maids.projdemo.librarysystem.service.BorrowingServiceImp;
import com.maids.projdemo.librarysystem.service.PatronServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {
    @Autowired
    private PatronServiceImp patronService;

    @Autowired
    private BorrowingServiceImp borrowingService;

    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecord borrowingRecord = patronService.borrowBook(bookId, patronId);
        return new ResponseEntity<>(borrowingRecord, HttpStatus.CREATED);
    }
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingService.returnBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
