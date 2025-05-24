package com.example.library_management.services;

import com.example.library_management.DTOs.CreateAdminDTO;
import com.example.library_management.exceptions.EntityNotFoundException;
import com.example.library_management.models.Admin;

import java.util.List;

public interface AdminServices {
    List<Admin> getAllAdmins();

    public Admin getAdminById(Long id) throws EntityNotFoundException;

    public Admin create(CreateAdminDTO adminDTO);

}
