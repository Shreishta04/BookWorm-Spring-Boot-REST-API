package com.example.library_management.DTOs;

import jakarta.validation.constraints.NotBlank;

public class CreateAdminDTO {
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
