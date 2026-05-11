package com.gestionsalles.app.repositories;

import com.gestionsalles.app.models.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends UserRepository<Admin> {
    @Query("select a from Admin a where a.name = :name")
    Optional<Admin> findByName(@Param("name") String name);
}
