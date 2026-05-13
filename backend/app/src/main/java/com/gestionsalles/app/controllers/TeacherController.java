package com.example.demo.controllers;

import com.example.demo.models.Teacher;
import com.example.demo.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
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

    @GetMapping("/info/{id}")
    public ResponseEntity<List<Object[]>> getInfo(@PathVariable Long id){

        List<Object[]> info= teacherServ.getInfo(id);

        ResponseEntity.ok(info);

        for(int i=0;i<id;i++){
            List<Object[]> t= teacherServ.getInfo(new Long(i));

            info.addAll(t);
        }

        return ResponseEntity.ok(info);


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
