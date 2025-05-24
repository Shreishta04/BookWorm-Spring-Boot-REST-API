package com.example.library_management.controllers;


import com.example.library_management.DTOs.CreateAdminDTO;
import com.example.library_management.exceptions.EntityNotFoundException;
import com.example.library_management.models.Admin;
import com.example.library_management.services.AdminServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;

    @GetMapping()
    public ResponseEntity<List<Admin>> getAllAdmins(){
        List<Admin> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable @NotNull Long id){
        try{
            Admin admin = adminService.getAdminById(id);
            return new ResponseEntity<>(admin, HttpStatusCode.valueOf(200));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateAdminDTO adminDTO){
        try{
            Admin admin = adminService.create(adminDTO);
            return new ResponseEntity<>(admin, HttpStatusCode.valueOf(200));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(409));
        }
    }
}
