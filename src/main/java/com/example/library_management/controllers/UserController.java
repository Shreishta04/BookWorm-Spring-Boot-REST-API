package com.example.library_management.controllers;

import com.example.library_management.DTOs.CreateUserDTO;
import com.example.library_management.exceptions.EntityNotFoundException;
import com.example.library_management.exceptions.UniqueEntityException;
import com.example.library_management.models.User;
import com.example.library_management.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatusCode.valueOf(200));
    }

    @GetMapping("/findbyid")
    public ResponseEntity<?> getUserById(@RequestBody Long id){
        try{
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user,HttpStatusCode.valueOf(200));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserDTO userDTO){
        try{
            User user = userService.create(userDTO);
            return new ResponseEntity<>(user,HttpStatusCode.valueOf(200));
        } catch (UniqueEntityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(409));
        }
    }

}
