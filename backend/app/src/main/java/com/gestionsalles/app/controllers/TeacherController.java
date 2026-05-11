package com.gestionsalles.app.controllers;

import com.gestionsalles.app.models.Teacher;
import com.gestionsalles.app.models.User;
import com.gestionsalles.app.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherServ;

    @GetMapping
    public ResponseEntity<List<Teacher>> getAll() {
        return teacherServ.getAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id){
        return teacherServ.getTeacherById(id);
    }


    //using parent User repo method
    @GetMapping("/{nameoremail}")
    public ResponseEntity<List<User>> getAllByNameOrEmail(@PathVariable String nameoremail){
        return teacherServ.getTeachersByEmailOrName(nameoremail);
    }

    @PostMapping("/add")
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher){
        return teacherServ.addTeacher(teacher);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id,@RequestBody Teacher teacher){
        return teacherServ.updateTeacherById(id,teacher);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Teacher> updatePartialTeacher(@PathVariable Long id, @RequestBody Teacher teacher){
        return teacherServ.updatePartialTeacherById(id,teacher);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Teacher> deleteTeacherById(@PathVariable Long id){
        return teacherServ.deleteTeacherById(id);
    }
}
