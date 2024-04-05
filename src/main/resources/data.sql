-- Insert sample books
INSERT INTO books (title, author, publication_year, isbn, available)
VALUES
    ('The Great Gatsby', 'F. Scott Fitzgerald', 1925, '9780743273565', true),
    ('To Kill a Mockingbird', 'Harper Lee', 1960, '9780061120084', true),
    ('1984', 'George Orwell', 1949, '9780451524935', true),
    ('Pride and Prejudice', 'Jane Austen', 1813, '9780553213102', true),
    ('The Catcher in the Rye', 'J.D. Salinger', 1951, '9780316769488', true);

-- Insert sample patrons
INSERT INTO patrons (name, contact_information)
VALUES
    ('John Doe', 'john.doe@example.com'),
    ('Jane Smith', 'jane.smith@example.com'),
    ('Alice Johnson', 'alice.johnson@example.com'),
    ('Bob Williams', 'bob.williams@example.com');

-- Insert sample borrowing records
INSERT INTO borrowing_records (book_id, patron_id, borrow_date, return_date)
VALUES
    (1, 1, '2023-01-01', NULL),
    (2, 2, '2023-01-02', NULL),
    (3, 3, '2023-01-03', NULL),
    (4, 4, '2023-01-04', NULL),
    (5, 1, '2023-01-05', NULL);