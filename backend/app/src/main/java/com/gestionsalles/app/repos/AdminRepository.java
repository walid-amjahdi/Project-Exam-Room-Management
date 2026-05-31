package com.gestionsalles.app.repos;

import com.gestionsalles.app.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByEmailAndPassword(String email, String password);
    @Query("select a.id,a.name,a.role,count(r.id) from Admin a join a.reservations_admin r group by a.id, a.name, a.role order by a.id")
    List<Object[]> getAdminInfo();

}
