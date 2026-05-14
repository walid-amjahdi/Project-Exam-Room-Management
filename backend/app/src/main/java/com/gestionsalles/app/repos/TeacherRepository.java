package com.gestionsalles.app.repos;

import com.gestionsalles.app.models.Role;
import com.gestionsalles.app.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByEmailAndPassword(String email, String password);
}
