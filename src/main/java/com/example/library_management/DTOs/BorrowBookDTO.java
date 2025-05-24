package com.example.library_management.DTOs;

import jakarta.validation.constraints.NotNull;

public class BorrowBookDTO {
    @NotNull(message = " User ID cannot be null")
    private Long userId;

    @NotNull(message ="Book ID cannot be null")
    private Long bookId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
