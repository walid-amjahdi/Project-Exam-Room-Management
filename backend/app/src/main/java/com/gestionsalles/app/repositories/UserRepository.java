package com.gestionsalles.app.repositories;


import com.gestionsalles.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //parent class logic

    List<User> findAllByEmail(String email);

    List<User> findAllByName(String name);
}
