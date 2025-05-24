package com.example.library_management.services;

import com.example.library_management.DTOs.*;
import com.example.library_management.exceptions.EntityNotFoundException;
import com.example.library_management.exceptions.UnauthorisedAdminException;
import com.example.library_management.models.Book;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public interface BookServices {
    List<Book> getAllBooks();

    Book getBookById(Long id) throws EntityNotFoundException;

    public Book create(CreateBookDTO bookDTO) throws EntityNotFoundException, MethodArgumentNotValidException, DataIntegrityViolationException;

    public Book update(UpdateBookDTO updateBookDTO) throws EntityNotFoundException, UnauthorisedAdminException, MethodArgumentNotValidException, DataIntegrityViolationException;

    public String delete(DeleteBookDTO deleteBookDTO) throws EntityNotFoundException, UnauthorisedAdminException, MethodArgumentNotValidException, DataIntegrityViolationException;

    public List<Book> searchByAuthor(SearchByAuthorDTO searchByAuthorDTO) throws EntityNotFoundException, MethodArgumentNotValidException, DataIntegrityViolationException;

    public List<Book> searchByTitle(SearchByTitleDTO searchByTitleDTO) throws EntityNotFoundException, MethodArgumentNotValidException, DataIntegrityViolationException;

    public String borrowBook(BorrowBookDTO borrowBookDTO) throws EntityNotFoundException, MethodArgumentNotValidException, DataIntegrityViolationException;

    public String returnBook(ReturnBookDTO returnBookDTO) throws EntityNotFoundException, MethodArgumentNotValidException, DataIntegrityViolationException;
}
