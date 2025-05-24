package com.example.library_management.services;

import com.example.library_management.DTOs.CreateUserDTO;
import com.example.library_management.exceptions.EntityNotFoundException;
import com.example.library_management.exceptions.UniqueEntityException;
import com.example.library_management.models.User;
import com.example.library_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServices{
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with ID: "+id+" not found!"));
    }

    public User create(CreateUserDTO userDTO) throws UniqueEntityException {
        User user = new User();
        user.setName(userDTO.getName());
        return userRepository.save(user);
    }
}
