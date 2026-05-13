package com.example.demo.services;

import com.example.demo.models.Admin;
import com.example.demo.models.Reservation;
import com.example.demo.repos.AdminRepository;
import com.example.demo.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepo;
    private final UserRepository userRepo;


    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    public Optional<Admin> addAdmin(Admin admin) {
        Optional<Admin> a=findByEmail(admin.getEmail());
        if(!a.isPresent() && !userRepo.existsByEmail(admin.getEmail())){
            return Optional.of(adminRepo.save(admin));
        }
        return Optional.empty();
    }

    public Optional<Admin> updateAdminById(Long id, Admin admin) {
        Optional<Admin> admin1=findById(id);
        if(admin1.isPresent()){
            admin.setId(id);
            adminRepo.save(admin);
            return findByEmail(admin.getEmail());
        }
        return Optional.empty();
    }

    public Optional<Admin> patchAdminById(Long id, Admin admin) {
        Optional<Admin> admin1=findById(id);

        if(admin1.isPresent()){
            if (admin.getEmail() != null) {
                admin1.get().setEmail(admin.getEmail());
            }
            if (admin.getName() != null) {
                admin1.get().setName(admin.getName());
            }

            if (admin.getRole() != null) {
                admin1.get().setRole(admin.getRole());
            }
            if (admin.getPassword() != null) {
                admin1.get().setPassword(admin.getPassword());
            }
            if(admin.getIsSudo()!=admin1.get().getIsSudo()){
                admin1.get().setIsSudo(admin.getIsSudo());
            }
            adminRepo.save(admin1.get());
            return admin1;
        }
            return Optional.empty();
    }

    public Optional<Admin> deleteAdminById(Long id) {
        Optional<Admin> admin=findById(id);
        if(admin.isPresent()){
            adminRepo.deleteById(id);
            return admin;
        }
        return Optional.empty();
    }

    public Optional<Admin> findById(Long id){
        return adminRepo.findById(id);
    }
    public Optional<Admin> findByEmail(String email) {
        return adminRepo.findByEmail(email);
    }

    public void manageUsers(){

    }
    public void manageRooms(){

    }
    public void validateReservation(Reservation reservation){

    }

    public void rejectReservation(Reservation reservation){


    }
}
