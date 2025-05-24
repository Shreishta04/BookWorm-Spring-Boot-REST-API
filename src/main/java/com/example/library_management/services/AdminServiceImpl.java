package com.example.library_management.services;

import com.example.library_management.DTOs.CreateAdminDTO;
import com.example.library_management.exceptions.EntityNotFoundException;
import com.example.library_management.models.Admin;
import com.example.library_management.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminServices{

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long id) throws EntityNotFoundException {
        return adminRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Admin with ID: "+id+" not found."));
    }

    public Admin create(CreateAdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setName(adminDTO.getName());
        return adminRepository.save(admin);
    }
}
