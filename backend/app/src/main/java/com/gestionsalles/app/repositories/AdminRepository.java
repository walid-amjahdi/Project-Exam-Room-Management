package com.gestionsalles.app.repositories;

import com.gestionsalles.app.models.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends UserRepository<Admin> {
}
