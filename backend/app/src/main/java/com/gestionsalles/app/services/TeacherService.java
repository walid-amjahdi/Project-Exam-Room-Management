package com.gestionsalles.app.services;

import com.gestionsalles.app.models.Teacher;
import com.gestionsalles.app.models.User;
import com.gestionsalles.app.repositories.TeacherRepository;
import com.gestionsalles.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gestionsalles.app.models.Role.TEACHER;

@Service
@RequiredArgsConstructor
public class TeacherService{

    private final UserRepository userRepo;

    private final TeacherRepository teacherRepo;


    public ResponseEntity<List<Teacher>> getAllTeachers() {
        if(teacherRepo.findAll().isEmpty()) {
            List<Teacher> teachers = new ArrayList<>();
            for(int i=0; i < 10; i++ ) {
                teachers.add(new Teacher());
            }

            for(Teacher t:teachers){
                t.setName("Teacher "+(teachers.indexOf(t)+1));
                t.setEmail("Teacher"+(teachers.indexOf(t)+1)+"email@gmail.com");
                t.setPassword("password"+(teachers.indexOf(t)+1));
                t.setDepartement("Departement "+(teachers.indexOf(t)+1));
                t.setGrade("Grade"+(teachers.indexOf(t)+1));
                t.setRole(TEACHER);
                teacherRepo.save(t);
            }
            return ResponseEntity.ok(teachers);
        }
        return ResponseEntity.ok(teacherRepo.findAll());
    }

    public ResponseEntity<Teacher> getTeacherById(Long id) {
        Optional<Teacher> teacher= teacherRepo.findById(id);
        if(teacher.isPresent()){
            return ResponseEntity.ok(teacher.get());
        }
        return ResponseEntity.notFound().build();
    }


    //using parent repo method here ^ ^
    public ResponseEntity<List<User>> getTeachersByEmailOrName(String nameoremail) {
        if(nameoremail.contains("@")){
            return ResponseEntity.ok(userRepo.findAllByEmail(nameoremail));
        }
        return ResponseEntity.ok(userRepo.findAllByName(nameoremail));

    }

    public ResponseEntity<Teacher> addTeacher(Teacher teacher) {
        return ResponseEntity.ok(teacherRepo.save(teacher));
    }

    public ResponseEntity<Teacher> updateTeacherById(Long id, Teacher teacher) {
        Optional<Teacher> existingTeacher= teacherRepo.findById(id);
        if(existingTeacher.isPresent()){
            return ResponseEntity.ok(teacherRepo.save(teacher));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Teacher> updatePartialTeacherById(Long id, Teacher teacher) {
        Optional<Teacher> existingTeacher= teacherRepo.findById(id);

        if(existingTeacher.isPresent()){
            if(teacher.getDepartement()!=null){
                existingTeacher.get().setDepartement(teacher.getDepartement());
            }
            if(teacher.getEmail()!=null){
                existingTeacher.get().setEmail(teacher.getEmail());
            }
            if(teacher.getGrade()!=null){
                existingTeacher.get().setGrade(teacher.getGrade());
            }
            if(teacher.getName()!=null){
                existingTeacher.get().setName(teacher.getName());
            }
            if(teacher.getPassword()!=null){
                existingTeacher.get().setPassword(teacher.getPassword());
            }
            if(teacher.getRole()!=null){
                existingTeacher.get().setRole(teacher.getRole());
            }
            return ResponseEntity.ok(teacherRepo.save(existingTeacher.get()));
        }
        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<Teacher> deleteTeacherById(Long id) {
        Optional<Teacher> existingTeacher= teacherRepo.findById(id);

        if(existingTeacher.isPresent()){
            teacherRepo.deleteById(id);
            return ResponseEntity.ok(existingTeacher.get());
        }
        return ResponseEntity.notFound().build();
    }
}
