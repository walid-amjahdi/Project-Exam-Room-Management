package com.gestionsalles.app.services;

import com.gestionsalles.app.models.Admin;
import com.gestionsalles.app.models.User;
import com.gestionsalles.app.repositories.AdminRepository;
import com.gestionsalles.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gestionsalles.app.models.Role.ADMIN;


@Service
@RequiredArgsConstructor
public class AdminService {

    //parent repo
    private final UserRepository userRepo;

    //children repo
    private final AdminRepository adminRepo;



    public ResponseEntity<List<User>> findByNameOrEmail (String nameoremail) {
        List<User> admin;
        if(nameoremail.contains("@")){
            admin=userRepo.findAllByEmail(nameoremail);
        }else{
            admin=userRepo.findAllByName(nameoremail);
        }
        if(admin.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admin);
    }

    public ResponseEntity<List<Admin>> getAllAdmins() {
        if(adminRepo.findAll().isEmpty()) {
            List<Admin> admins=new ArrayList<>();

            for(int i=0; i < 10; i++ ) {
                admins.add(new Admin());
            }

            for(Admin t:admins) {
                t.setName("Admin " + (admins.indexOf(t) + 1));
                t.setEmail("Admin" + (admins.indexOf(t) + 1) + "email@gmail.com");
                t.setPassword("password" + (admins.indexOf(t) + 1));
                t.setCanManageRooms(admins.indexOf(t) == admins.indexOf(t) % 2);
                t.setCanManageUsers(admins.indexOf(t) == admins.indexOf(t) % 2);
                t.setRole(ADMIN);
                adminRepo.save(t);
            }
            return ResponseEntity.ok(adminRepo.findAll());
        }
        return ResponseEntity.ok(adminRepo.findAll());
    }
    //admin save

    public ResponseEntity<Admin> addAdmin(Admin admin) {
        return ResponseEntity.ok(adminRepo.save(admin));
    }

    public ResponseEntity<Admin> updateAdminById(Long id, Admin admin) {
         Optional<Admin> existingAdmin= adminRepo.findById(id);
         if(existingAdmin.isPresent()){
             admin.setId(existingAdmin.get().getId());
             return ResponseEntity.ok(adminRepo.save(admin));
         }
         return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Admin> deleteAdminById(Long id) {
        if(adminRepo.existsById(id)){
            adminRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Admin> patchAdminById(Long id, Admin admin) {
        Optional<Admin> existingAdmin= adminRepo.findById(id);
        if(existingAdmin.isPresent()){
            if(admin.getName()!=null){
                existingAdmin.get().setName(admin.getName());
            }
            if(admin.getEmail()!=null){
                existingAdmin.get().setEmail(admin.getEmail());
            }
            if(admin.getPassword()!=null){
                existingAdmin.get().setPassword(admin.getPassword());
            }
            if(admin.getRole()!=null){
                existingAdmin.get().setRole(admin.getRole());
            }
            return ResponseEntity.ok(adminRepo.save(existingAdmin.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
