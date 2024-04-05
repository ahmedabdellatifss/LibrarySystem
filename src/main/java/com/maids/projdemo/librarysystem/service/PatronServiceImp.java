package com.maids.projdemo.librarysystem.service;

import com.maids.projdemo.librarysystem.entity.BorrowingRecord;
import com.maids.projdemo.librarysystem.entity.Patron;
import com.maids.projdemo.librarysystem.exceptions.ResourceNotFoundException;
import com.maids.projdemo.librarysystem.repository.BorrowingRepository;
import com.maids.projdemo.librarysystem.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatronServiceImp implements PatronService{
    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BorrowingRepository borrowingRecordRepository;
    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    @Override
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Override
    @Transactional
    public Patron updatePatron(Long id, Patron patron) {
        Optional<Patron> optionalPatron = patronRepository.findById(id);
        if (optionalPatron.isPresent()) {
            Patron existingPatron = optionalPatron.get();
            existingPatron.setName(patron.getName());
            existingPatron.setContactInformation(patron.getContactInformation());
            // Update other properties as needed
            return patronRepository.save(existingPatron);
        } else {
            throw new ResourceNotFoundException("Patron not found with id: " + id);
        }
    }

    @Override
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }

    @Override
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Optional<BorrowingRecord> borrowingRecordOptional = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);

        if (borrowingRecordOptional.isPresent()) {
            BorrowingRecord borrowingRecord = borrowingRecordOptional.get();
            borrowingRecord.setReturnDate(LocalDate.now());

            return borrowingRecordRepository.save(borrowingRecord);
        } else {
            throw new ResourceNotFoundException("No active borrowing record found for the given book and patron.");
        }
    }

    @Override
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        Optional<BorrowingRecord> borrowingRecordOptional = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);

        if (borrowingRecordOptional.isPresent()) {
            BorrowingRecord borrowingRecord = borrowingRecordOptional.get();
            borrowingRecord.setReturnDate(LocalDate.now());
            return borrowingRecordRepository.save(borrowingRecord);
        } else {
            throw new ResourceNotFoundException("No active borrowing record found for the given book and patron.");
        }
    }
}
