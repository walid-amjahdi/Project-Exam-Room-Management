package com.example.demo.services;


import com.example.demo.models.Reservation;
import com.example.demo.repos.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepo;
    private final TeacherService teacherServ;


    public Optional<Reservation> findReservationByDate(Date reservationDate) {
        return reservationRepo.findByReservationDateEquals(reservationDate);
    }

    public List<Reservation> findAllReservations() {
        return reservationRepo.findAll();
    }


    public Optional<Reservation> findReservationById(Long id) {
        Optional<Reservation> r= reservationRepo.findById(id);

        if(r.isPresent()){
            return r;
        }
        return Optional.empty();
    }

    public List<Reservation> findReservationByAdminEmail(String email){
        return reservationRepo.findReservationByAdmin_Email(email);
    }
    public List<Reservation> findReservationByAdminId(Long id){
        return reservationRepo.findReservationsByAdmin_Id(id);
    }

    public List<Reservation> findReservationByRoomName(String roomName){
        return reservationRepo.findReservationsByRoom_Name(roomName);
    }
    public List<Reservation> findReservationByRoomId(Long id){
        return reservationRepo.findReservationsByRoom_Id(id);
    }


    public Optional<Reservation> addReservation(Reservation reservation) {
        Optional<Reservation> r=findReservationByDate(reservation.getReservationDate());
        if(!r.isPresent()){
            reservationRepo.save(reservation);
            return findReservationByDate(reservation.getReservationDate());
        }
        return Optional.empty();
    }

    public Optional<Reservation> updateReservationById(Long id, Reservation reservation) {
        Optional<Reservation> r= findReservationById(id);
        if(r.isPresent()){
            reservation.setId(id);
            reservationRepo.save(reservation);
            return reservationRepo.findById(id);
        }
        return Optional.empty();
    }

    public Optional<Reservation> deleteReservationById(Long id) {
        Optional<Reservation> r=findReservationById(id);
        if(r.isPresent()){
            reservationRepo.deleteById(id);
            return r;
        }
        return Optional.empty();
    }


    public List<Reservation> findByTeacherEmail(String email){
        List<Reservation> r = reservationRepo.findReservationByTeacherEmail(email);
        if(!r.isEmpty()){
            return r;
        }
        return null;
    }


    public List<Reservation> findByTeacherId(Long id) {
        return reservationRepo.findReservationByTeacherId(id);
    }

    public void createReservation() {




    }
    public void updateReservation() {


    }


    public void cancelReservation(){


    }


    public boolean checkConflicts() {
        return false;
    }

}
