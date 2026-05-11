package com.gestionsalles.app.services;

import com.gestionsalles.app.models.Admin;
import com.gestionsalles.app.models.Role;
import com.gestionsalles.app.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService{

    @Autowired
    private AdminRepository adminRepo;

    public ResponseEntity<Admin> findByName (String name){
        Optional<Admin> admin= adminRepo.findByName(name);
        if(!admin.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admin.get());
    }

    public ResponseEntity<List<Admin>> getAllAdmins() {
        if(adminRepo.findAll().isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail("admin@admin.com");
            admin.setPassword("admin");
            admin.setName("admin");
            admin.setRole(Role.ADMIN);
            adminRepo.save(admin);
            return ResponseEntity.ok(adminRepo.findAll());
        }
        return ResponseEntity.ok(adminRepo.findAll());
    }
}
