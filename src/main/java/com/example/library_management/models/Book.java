package com.example.library_management.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title cannot be blank.")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Author cannot be blank.")
    private String author;

    @Column(nullable = false)
    @NotBlank(message = "ISBN cannot be blank.")
    private String isbn;

    @Column(nullable = false)
    @NotNull(message = "Publication Year cannot be blank.")
    private Long publicationYear;

    @Column(nullable = false)
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "adminId")
    @JsonBackReference
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;


    public Book(){}

    public Book(String title, String author, String isbn, Long publicationYear){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public Long getPublicationYear() {
        return publicationYear;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublicationYear(Long publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
