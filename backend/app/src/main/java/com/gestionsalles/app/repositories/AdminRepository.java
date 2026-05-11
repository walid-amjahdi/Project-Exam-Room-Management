package com.gestionsalles.app.repositories;

import com.gestionsalles.app.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

    //admin specific logic


}
