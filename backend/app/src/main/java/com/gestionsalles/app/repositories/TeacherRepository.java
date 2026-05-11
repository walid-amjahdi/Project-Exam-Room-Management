package com.gestionsalles.app.repositories;

import com.gestionsalles.app.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    //teacher specific logic

}
