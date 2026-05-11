package com.gestionsalles.app.controllers;

import com.gestionsalles.app.models.Admin;
import com.gestionsalles.app.models.User;
import com.gestionsalles.app.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminServ;

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return adminServ.getAllAdmins();
    }

    @GetMapping("/{nameoremail}")
    public ResponseEntity<List<User>> getAdminByName(@PathVariable String nameoremail) {
        return adminServ.findByNameOrEmail(nameoremail);
    }

    @PostMapping("/add")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        return adminServ.addAdmin(admin);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        return adminServ.updateAdminById(id,admin);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Admin> patchAdmin(@PathVariable Long id,@RequestBody Admin admin) {
        return adminServ.patchAdminById(id,admin);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Admin> deleteAdminById(@PathVariable Long id){
        return adminServ.deleteAdminById(id);
    }
}
