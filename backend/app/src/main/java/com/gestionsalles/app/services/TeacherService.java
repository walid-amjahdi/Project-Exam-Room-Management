package com.gestionsalles.app.services;

import com.gestionsalles.app.models.Reservation;
import com.gestionsalles.app.models.Role;
import com.gestionsalles.app.models.Teacher;
import com.gestionsalles.app.models.User;
import com.gestionsalles.app.repos.TeacherRepository;
import com.gestionsalles.app.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

    @Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepo;
    private final UserRepository userRepo;

    //OneToMany
    private final ReservationService reservationServ;


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

    public Optional<Teacher> login(String email, String password) {
        return teacherRepo.findByEmailAndPassword(email, password);
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
            if(teacher.getDepartment()!=null){
                teacher1.get().setDepartment(teacher.getDepartment());
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


    public List<Object[]> getInfo(Long id){
        Optional<User> t=userRepo.findFirstByRole(Role.TEACHER);
        if(t.isPresent() && id < t.get().getId()){
            return userRepo.findEmailAndNameAndRoleById(id+ t.get().getId() );
        }else if(!t.isPresent()){
            return null;
        }
        return userRepo.findEmailAndNameAndRoleById(id);
    }
}
