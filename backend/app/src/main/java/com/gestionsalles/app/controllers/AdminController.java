package com.example.demo.controllers;

import com.example.demo.models.Admin;
import com.example.demo.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminServ;

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins=adminServ.getAllAdmins();
        if(admins.isEmpty()){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(admins);
    }

    @PostMapping("/add")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        Optional<Admin> adm= adminServ.addAdmin(admin);
        if(adm.isPresent()){
            return ResponseEntity.ok(adm.get());
        }
        return ResponseEntity.ok(adminServ.findByEmail(admin.getEmail()).get());

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        Optional<Admin> admin1=adminServ.updateAdminById(id,admin);
        if(admin1.isPresent()){
            return ResponseEntity.ok(admin1.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Admin> patchAdmin(@PathVariable Long id,@RequestBody Admin admin) {
        Optional<Admin> admin1= adminServ.patchAdminById(id,admin);
        if(admin1.isPresent()){
            return ResponseEntity.ok(admin1.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Admin> deleteAdminById(@PathVariable Long id){
        Optional<Admin> admin= adminServ.deleteAdminById(id);
        if(admin.isPresent()){
            return ResponseEntity.ok(admin.get());
        }
        return ResponseEntity.notFound().build();
    }



}
