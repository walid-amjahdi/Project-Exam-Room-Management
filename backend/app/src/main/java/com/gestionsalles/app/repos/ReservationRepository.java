package com.example.demo.repos;

import com.example.demo.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Optional<Reservation> findByReservationDateEquals(Date date);

    List<Reservation> findReservationByTeacherEmail(String email);
    List<Reservation> findReservationByTeacherId(Long id);

    List<Reservation> findReservationsByAdmin_Id(Long id);
    List<Reservation> findReservationByAdmin_Email(String email);

    List<Reservation> findReservationsByRoom_Id(Long id);
    List<Reservation> findReservationsByRoom_Name(String roomName);




}
