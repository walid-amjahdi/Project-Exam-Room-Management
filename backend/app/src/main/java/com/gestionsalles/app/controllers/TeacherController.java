package com.gestionsalles.app.controllers;

import com.gestionsalles.app.models.Teacher;
import com.gestionsalles.app.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TeacherController {
    private final TeacherService teacherServ;

    @GetMapping
    public ResponseEntity<List<Teacher>> getAll() {
        List<Teacher> teachers= teacherServ.getAllTeachers();
        if(teachers.isEmpty()){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        var teacher = teacherServ.findById(id);
        if(teacher.isPresent()){
            return ResponseEntity.ok(teacher.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Teacher> getTeacherByEmail(@PathVariable String email) {
        var teacher = teacherServ.findByEmail(email);
        if(teacher.isPresent()){
            return ResponseEntity.ok(teacher.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<List<Object[]>> getInfo(@PathVariable Long id){
        List<Object[]> info= teacherServ.getInfo(id);
        return ResponseEntity.ok(info);
    }

    @PostMapping("/login")
    public ResponseEntity<Teacher> login(@RequestBody Teacher loginRequest) {
        var teacher = teacherServ.login(loginRequest.getEmail(), loginRequest.getPassword());
        if(teacher.isPresent()){
            return ResponseEntity.ok(teacher.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher){
        Optional<Teacher> teacher1=  teacherServ.addTeacher(teacher);
        if(teacher1.isPresent()){
            return ResponseEntity.ok(teacher1.get());
        }
        return ResponseEntity.ok(teacherServ.findByEmail(teacher.getEmail()).get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id,@RequestBody Teacher teacher){
        Optional<Teacher> teacher1= teacherServ.updateTeacherById(id,teacher);
        if(teacher1.isPresent()){
            return ResponseEntity.ok(teacher1.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Teacher> updatePartialTeacher(@PathVariable Long id, @RequestBody Teacher teacher){
        Optional<Teacher> teacher1= teacherServ.updatePartialTeacherById(id,teacher);
        if(teacher1.isPresent()){
            return ResponseEntity.ok(teacher1.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Teacher> deleteTeacherById(@PathVariable Long id){
        Optional<Teacher> teacher=teacherServ.deleteTeacherById(id);
        if(teacher.isPresent()){
            return ResponseEntity.ok(teacher.get());
        }
        return ResponseEntity.badRequest().build();
    }

}
