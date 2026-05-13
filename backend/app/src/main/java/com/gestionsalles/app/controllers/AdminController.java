package com.example.demo.controllers;

import com.example.demo.models.Admin;
import com.example.demo.services.AdminService;
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



    @GetMapping("/reservations")
    public ResponseEntity<List<Object[]>> getinfo(){
        List<Object[]> rows= adminServ.getNumberofDepartements();
        for(Object[] r:rows){
            System.out.println("==========");
            System.out.println("id : " + r[0]);
            System.out.println("name : " + r[1]);
            System.out.println("role : " + r[2]);
            System.out.println("number of departents : " + r[3]);
            System.out.println("==========");
        }

        List<Object[]> admininfo = adminServ.getNameAndEmail(0L);
        for(int i=1;i<10;i++){
            List<Object[]> a= adminServ.getNameAndEmail(new Long(i));
            admininfo.addAll(a);
        }
        for(Object[] r: admininfo){
            System.out.println("==========admin info===");
            for(int i=0;i<r.length;i++){
                System.out.println(i + " : " + r[i]);
            }
            System.out.println("==========");
        }
        List<Object[]> admininfos= adminServ.getEmail(10L);
        for(int i=1;i<10;i++){
            List<Object[]> a= adminServ.getEmail(new Long(i));
            admininfos.addAll(a);
        }
        for(Object[] r:admininfos){
            System.out.println("==========admin info===");
            for(int i=0;i<r.length;i++){
                System.out.println(i + " : " + r[i]);
            }
            System.out.println("==========");
        }
        return ResponseEntity.ok(admininfos);
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
