package com.example.library_management.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SearchByTitleDTO {
    @NotNull(message = " User ID cannot be null")
    private Long userId;

    @NotBlank(message = "Title cannot be blank")
    private String title;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
