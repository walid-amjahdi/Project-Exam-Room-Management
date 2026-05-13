package com.example.demo.services;

import com.example.demo.models.Reservation;
import com.example.demo.models.Room;
import com.example.demo.models.Teacher;
import com.example.demo.repos.TeacherRepository;
import com.example.demo.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepo;
    private final UserRepository userRepo;

    //OneToMany
    private ReservationService reservationServ;


    public List<Teacher> getAllTeachers() {
        return teacherRepo.findAll();
    }

    public Optional<Teacher> addTeacher(Teacher teacher) {
        Optional<Teacher> t=findByEmail(teacher.getEmail());
        if(!t.isPresent() && !userRepo.existsByEmail(teacher.getEmail())){

            return Optional.of(teacherRepo.save(teacher));
        }
        return Optional.empty();
    }

    public Optional<Teacher> findByEmail(String email) {
        return teacherRepo.findByEmail(email);
    }

    public Optional<Teacher> updateTeacherById(Long id, Teacher teacher) {
        Optional<Teacher> teacher1= findById(id);
        if(teacher1.isPresent()){
            teacher.setId(id);
            teacherRepo.save(teacher);
            return Optional.of(teacher);
        }
        return Optional.empty();
    }

    public Optional<Teacher> updatePartialTeacherById(Long id, Teacher teacher) {
        Optional<Teacher> teacher1 = findById(id);
        if(teacher1.isPresent()){
            if(teacher.getEmail()!=null){
                teacher1.get().setEmail(teacher.getEmail());
            }
            if(teacher.getName()!=null){
                teacher1.get().setName(teacher.getName());
            }
            if(teacher.getGrade()!=null){
                teacher1.get().setGrade(teacher.getGrade());
            }
            if(teacher.getRole()!=null){
                teacher1.get().setRole(teacher.getRole());
            }
            if(teacher.getDepartement()!=null){
                teacher1.get().setDepartement(teacher.getDepartement());
            }
            if(teacher.getPassword()!=null){
                teacher1.get().setPassword(teacher.getPassword());
            }
            teacherRepo.save(teacher1.get());
            return teacher1;
        }
        return Optional.empty();
    }

    public Optional<Teacher> deleteTeacherById(Long id) {
        Optional<Teacher> teacher= findById(id);
        if(teacher.isPresent()){
            teacherRepo.deleteById(id);
            return teacher;
        }
        return Optional.empty();
    }

    public Optional<Teacher> findById(Long id) {
        return teacherRepo.findById(id);
    }

    public List<Reservation> findTeacherReservations(Teacher teacher){
        return teacher.getTeacher_reservations();
    }

    public Optional<Teacher> addReservationToTeacher(List<Reservation> reservations,Teacher teacher){

        Optional<Teacher> t= teacherRepo.findById(teacher.getId());

        if(t.isPresent()){
            for(Reservation r : reservations){
                reservationServ.addReservation(r);
            }
            return Optional.of(teacherRepo.save(t.get()));
        }
        return Optional.empty();


    }


    public void requestReservation(){

    }

    public void viewReservations(){

    }

    public void cancelReservation(Reservation reservation){

    }

    public List<Room> viewAvailableRooms (Date date){


        return null;
    }
}
