package com.gestionsalles.app.controllers;

import com.gestionsalles.app.models.Admin;
import com.gestionsalles.app.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        var admin = adminServ.findById(id);
        if(admin.isPresent()){
            return ResponseEntity.ok(admin.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Admin> getAdminByEmail(@PathVariable String email) {
        var admin = adminServ.findByEmail(email);
        if(admin.isPresent()){
            return ResponseEntity.ok(admin.get());
        }
        return ResponseEntity.notFound().build();
    }



    @GetMapping("/reservations")
    public ResponseEntity<List<Admin>> getinfo(){
        List<Admin> admins = adminServ.getAllAdmins();
        return ResponseEntity.ok(admins);
    }










    @PostMapping("/login")
    public ResponseEntity<Admin> login(@RequestBody Admin loginRequest) {
        var admin = adminServ.login(loginRequest.getEmail(), loginRequest.getPassword());
        if(admin.isPresent()){
            return ResponseEntity.ok(admin.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        Optional<Admin> adm= adminServ.addAdmin(admin);
        if(adm.isPresent()){
            return ResponseEntity.ok(adm.get());
        }
        Optional<Admin> existing = adminServ.findByEmail(admin.getEmail());
        if(existing.isPresent()){
            return ResponseEntity.ok(existing.get());
        }
        return ResponseEntity.badRequest().build();

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
