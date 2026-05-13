package com.example.demo.repos;

import com.example.demo.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByEmail(String email);
    @Query("select a.id,a.name,a.role,count(r.id) from Admin a join a.reservations_admin r group by a.name order by a.id")
    List<Object[]> getAdminInfo();

}
