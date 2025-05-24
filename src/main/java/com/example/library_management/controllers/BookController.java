package com.example.library_management.controllers;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.library_management.DTOs.*;
import com.example.library_management.exceptions.EntityNotFoundException;
import com.example.library_management.exceptions.UnauthorisedAdminException;
import com.example.library_management.models.Book;
import com.example.library_management.services.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookServiceImpl bookServices;

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookServices.getAllBooks();
        return new ResponseEntity<>(books, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            Book book = bookServices.getBookById(id);
            return new ResponseEntity<>(book, HttpStatusCode.valueOf(200));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateBookDTO bookDTO) {
        try{
            Book book = bookServices.create(bookDTO);
            return new ResponseEntity<>(book, HttpStatusCode.valueOf(200));
        } catch (MethodArgumentNotValidException | DataIntegrityViolationException e){
            return handleValidationExceptions(e);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateBookDTO updateBookDTO){
        try{
            Book book = bookServices.update(updateBookDTO);
            return new ResponseEntity<>(book, HttpStatusCode.valueOf(200));
        }  catch (MethodArgumentNotValidException | DataIntegrityViolationException e){
            return handleValidationExceptions(e);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (UnauthorisedAdminException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(401));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteBookDTO deleteBookDTO){
        try{
            String deletedBook = bookServices.delete(deleteBookDTO);
            return new ResponseEntity<>(deletedBook, HttpStatusCode.valueOf(200));
        } catch (MethodArgumentNotValidException | DataIntegrityViolationException e){
            return handleValidationExceptions(e);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (UnauthorisedAdminException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(401));
        }
    }

    @GetMapping("/search/author")
    public ResponseEntity<?> searchByAuthor(@Valid @RequestBody SearchByAuthorDTO searchByAuthorDTO){
        try{
            List<Book> books = bookServices.searchByAuthor(searchByAuthorDTO);
            return new ResponseEntity<>(books, HttpStatusCode.valueOf(200));
        } catch (MethodArgumentNotValidException | DataIntegrityViolationException e){
            return handleValidationExceptions(e);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @GetMapping("/search/title")
    public ResponseEntity<?> searchByTitle(@Valid @RequestBody SearchByTitleDTO searchByTitleDTO){
        try{
            List<Book> books= bookServices.searchByTitle(searchByTitleDTO);
            return new ResponseEntity<>(books, HttpStatusCode.valueOf(200));
        } catch (MethodArgumentNotValidException | DataIntegrityViolationException e){
            return handleValidationExceptions(e);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @PostMapping("/borrow")
    public ResponseEntity<?> borrow(@Valid @RequestBody BorrowBookDTO borrowBookDTO){
        try{
            String borrowed = bookServices.borrowBook(borrowBookDTO);
            return new ResponseEntity<>(borrowed, HttpStatusCode.valueOf(200));
        } catch (MethodArgumentNotValidException | DataIntegrityViolationException e){
            return handleValidationExceptions(e);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(401));
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> borrow(@Valid @RequestBody ReturnBookDTO returnBookDTO){
        try{
            String returned = bookServices.returnBook(returnBookDTO);
            return new ResponseEntity<>(returned, HttpStatusCode.valueOf(200));
        } catch (MethodArgumentNotValidException | DataIntegrityViolationException e){
            return handleValidationExceptions(e);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(401));
        }
    }

    private ResponseEntity<?> handleValidationExceptions(Exception e){
        StringBuilder errorMessage = new StringBuilder();
        if(e instanceof MethodArgumentNotValidException validEx){
            for(FieldError error : validEx.getFieldErrors()){
                errorMessage.append(error.getField()).append(":").append(error.getDefaultMessage()).append(" ; ");
            }
        }
        else if(e instanceof DataIntegrityViolationException){
            errorMessage.append("Invalid Book Data : A required field is missing or null");
        }
        else{
            errorMessage.append("Unexpected Error : ").append(e.getMessage());
        }
        System.err.println("Error : "+ errorMessage);
        return new ResponseEntity<>(errorMessage.toString(),HttpStatusCode.valueOf(400));
    }
}
