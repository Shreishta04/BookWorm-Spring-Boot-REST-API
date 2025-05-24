package com.example.library_management.services;

import com.example.library_management.DTOs.*;
import com.example.library_management.exceptions.EntityNotFoundException;
import com.example.library_management.exceptions.UnauthorisedAdminException;
import com.example.library_management.models.Admin;
import com.example.library_management.models.Book;
import com.example.library_management.models.User;
import com.example.library_management.repositories.AdminRepository;
import com.example.library_management.repositories.BookRepository;
import com.example.library_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookServices {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) throws EntityNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with ID: " + id + " not found."));
    }

    public Book create(CreateBookDTO bookDTO) throws EntityNotFoundException, MethodArgumentNotValidException, DataIntegrityViolationException {
        Book book = new Book();
        Admin admin = adminRepository.findById(bookDTO.getAdminId()).orElseThrow(() -> new EntityNotFoundException("Admin with ID: " + bookDTO.getAdminId() + " not found."));
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setIsAvailable(bookDTO.getIsAvailable());
        book.setAdmin(admin);
        admin.getBooks().add(book);
        adminRepository.save(admin);
        return bookRepository.save(book);
    }


    public Book update(UpdateBookDTO updateBookDTO) throws EntityNotFoundException, UnauthorisedAdminException, MethodArgumentNotValidException, DataIntegrityViolationException {
        Admin admin = adminRepository.findById(updateBookDTO.getAdminId()).orElseThrow(() -> new EntityNotFoundException("Admin with ID: " + updateBookDTO.getAdminId() + " not found."));
        Book book = bookRepository.findById(updateBookDTO.getBookId()).orElseThrow(() -> new EntityNotFoundException("Book with ID: " + updateBookDTO.getBookId() + " not found."));
        if (!book.getAdmin().getId().equals(admin.getId())) {
            throw new UnauthorisedAdminException("Admin Unauthorized to update Book details.");
        }
        book.setTitle(updateBookDTO.getTitle());
        book.setAuthor(updateBookDTO.getAuthor());
        book.setIsbn(updateBookDTO.getIsbn());
        book.setPublicationYear(updateBookDTO.getPublicationYear());
        book.setIsAvailable(updateBookDTO.getIsAvailable());
        book.setAdmin(admin);
        return bookRepository.save(book);
    }

    public String delete(DeleteBookDTO deleteBookDTO) throws EntityNotFoundException, UnauthorisedAdminException, MethodArgumentNotValidException, DataIntegrityViolationException {
        Admin admin = adminRepository.findById(deleteBookDTO.getAdminId()).orElseThrow(() -> new EntityNotFoundException("Admin with ID: " + deleteBookDTO.getAdminId() + " not found."));
        Book book = bookRepository.findById(deleteBookDTO.getBookId()).orElseThrow(() -> new EntityNotFoundException("Book with ID: " + deleteBookDTO.getBookId() + " not found."));
        if (!book.getAdmin().getId().equals(admin.getId())) {
            throw new UnauthorisedAdminException("Admin Unauthorized to delete Book details.");
        }
        bookRepository.delete(book);
        return "Book with ID: " + deleteBookDTO.getBookId() + " deleted!";
    }

    public List<Book> searchByAuthor(SearchByAuthorDTO searchByAuthorDTO) throws EntityNotFoundException, MethodArgumentNotValidException, DataIntegrityViolationException {
        User user = userRepository.findById(searchByAuthorDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User with ID: " + searchByAuthorDTO.getUserId() + " not found!"));
        List<Book> allBooks = bookRepository.findAll();
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getAuthor().equalsIgnoreCase(searchByAuthorDTO.getAuthor())) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }

    public List<Book> searchByTitle(SearchByTitleDTO searchByTitleDTO) throws EntityNotFoundException, MethodArgumentNotValidException, DataIntegrityViolationException {
        User user = userRepository.findById(searchByTitleDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User with ID: " + searchByTitleDTO.getUserId() + " not found!"));
        List<Book> allBooks = bookRepository.findAll();
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getTitle().equalsIgnoreCase(searchByTitleDTO.getTitle())) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }

    public String borrowBook(BorrowBookDTO borrowBookDTO) throws EntityNotFoundException, MethodArgumentNotValidException, DataIntegrityViolationException {
        User user = userRepository.findById(borrowBookDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User with ID: " + borrowBookDTO.getUserId() + " not found!"));
        Book book = bookRepository.findById(borrowBookDTO.getBookId()).orElseThrow(() -> new EntityNotFoundException("Book with ID: " + borrowBookDTO.getBookId() + " not found!"));
        if (user.getBooksList().contains(book)) {
            return "Book already Borrowed by current user: " + user.getName();
        }
        if (book.getIsAvailable()) {
            book.setUser(user);
            book.setIsAvailable(false);
            user.getBooksList().add(book);
            bookRepository.save(book);
            userRepository.save(user);
            return "Book Borrowed.";
        } else {
            return "Book Unavailable/Borrowed by other user.";
        }
    }

    public String returnBook(ReturnBookDTO returnBookDTO) throws EntityNotFoundException, MethodArgumentNotValidException, DataIntegrityViolationException {
        User user = userRepository.findById(returnBookDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User with ID: " + returnBookDTO.getUserId() + " not found!"));
        Book book = bookRepository.findById(returnBookDTO.getBookId()).orElseThrow(() -> new EntityNotFoundException("Book with ID: " + returnBookDTO.getBookId() + " not found!"));
        if (book.getUser() != null && (book.getUser().getId().equals(user.getId()))) {
            book.setIsAvailable(true);
            book.setUser(null);
            userRepository.save(user);
            bookRepository.save(book);
            return "Book Returned.";
        } else {
            return "User has already returned the book/hasn't borrowed it yet.";
        }
    }
}
