package com.maids.projdemo.librarysystem.service;

import org.springframework.transaction.annotation.Transactional;
import com.maids.projdemo.librarysystem.entity.Book;
import com.maids.projdemo.librarysystem.entity.BorrowingRecord;
import com.maids.projdemo.librarysystem.entity.Patron;
import com.maids.projdemo.librarysystem.exceptions.BookUnavailableException;
import com.maids.projdemo.librarysystem.exceptions.ResourceNotFoundException;
import com.maids.projdemo.librarysystem.repository.BookRepository;
import com.maids.projdemo.librarysystem.repository.BorrowingRepository;
import com.maids.projdemo.librarysystem.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingServiceImp implements BorrowingService{
    @Autowired
    private BorrowingRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Override
    public List<BorrowingRecord> getAllBorrowings() {
        return borrowingRecordRepository.findAll();
    }

    @Override
    public Optional<BorrowingRecord> getBorrowingById(Long id) {
        return borrowingRecordRepository.findById(id);
    }

    @Override
    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<Patron> patronOptional = patronRepository.findById(patronId);

        if (bookOptional.isPresent() && patronOptional.isPresent()) {
            Book book = bookOptional.get();
            Patron patron = patronOptional.get();

            if (!book.isAvailable()) {
                throw new BookUnavailableException("Book with id " + bookId + " is not available for borrowing.");
            }

            BorrowingRecord borrowingRecord = new BorrowingRecord(book, patron, LocalDate.now());
            borrowingRecordRepository.save(borrowingRecord);

            book.setAvailable(false);
            bookRepository.save(book);

            return borrowingRecord;
        } else {
            throw new ResourceNotFoundException("Book or patron not found with given ids.");
        }
    }

    @Override
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        Optional<BorrowingRecord> borrowingRecordOptional = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);

        if (borrowingRecordOptional.isPresent()) {
            BorrowingRecord borrowingRecord = borrowingRecordOptional.get();
            borrowingRecord.setReturnDate(LocalDate.now());
            borrowingRecordRepository.save(borrowingRecord);

            Book book = borrowingRecord.getBook();
            book.setAvailable(true);
            bookRepository.save(book);

            return borrowingRecord;
        } else {
            throw new ResourceNotFoundException("No active borrowing record found for the given book and patron.");
        }
    }
}
