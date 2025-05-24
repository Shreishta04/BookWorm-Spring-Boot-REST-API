package com.example.library_management.DTOs;

import jakarta.validation.constraints.NotNull;

public class DeleteBookDTO {
    @NotNull(message = " Admin ID cannot be null")
    private Long adminId;

    @NotNull(message ="Book ID cannot be null")
    private Long bookId;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
