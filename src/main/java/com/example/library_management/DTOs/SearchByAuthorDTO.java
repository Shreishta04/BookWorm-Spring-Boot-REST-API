package com.example.library_management.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SearchByAuthorDTO {
    @NotNull(message = " User ID cannot be null")
    private Long userId;

    @NotBlank(message = "Author name cannot be blank")
    private String author;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
