package com.example.library_management.services;

import com.example.library_management.DTOs.CreateUserDTO;
import com.example.library_management.exceptions.EntityNotFoundException;
import com.example.library_management.exceptions.UniqueEntityException;
import com.example.library_management.models.User;

import java.util.List;

public interface UserServices {
    List<User> getAllUsers();

    User getUserById(Long id) throws EntityNotFoundException;

    User create(CreateUserDTO userDTO) throws UniqueEntityException;
}
