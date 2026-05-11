package com.gestionsalles.app.controllers;

import com.gestionsalles.app.models.Admin;
import com.gestionsalles.app.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminServ;

    @GetMapping("/")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return adminServ.getAllAdmins();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Admin> getAdminByName(@PathVariable String name){
        return adminServ.findByName(name);
    }

}
