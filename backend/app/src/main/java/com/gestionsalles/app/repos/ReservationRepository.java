package com.gestionsalles.app.repos;

import com.gestionsalles.app.models.Reservation;
import com.gestionsalles.app.models.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByReservationDate(LocalDate reservationDate);
    List<Reservation> findByReservationDateAndRoom_Name(LocalDate reservationDate, String roomName);

    List<Reservation> findReservationByTeacherEmail(String email);
    List<Reservation> findReservationByTeacherId(Long id);

    List<Reservation> findReservationsByAdmin_Id(Long id);
    List<Reservation> findReservationByAdmin_Email(String email);

    List<Reservation> findReservationsByRoom_Id(Long id);
    List<Reservation> findReservationsByRoom_Name(String roomName);

    // Query to find conflicting reservations for a specific room on a specific date
    // with overlapping times (excluding rejected reservations)
    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId " +
           "AND r.reservationDate = :date " +
           "AND r.status != 'REJECTED' " +
           "AND ((r.startTime < :endTime AND r.endTime > :startTime))")
    List<Reservation> findConflictingReservations(
        @Param("roomId") Long roomId,
        @Param("date") LocalDate date,
        @Param("startTime") LocalTime startTime,
        @Param("endTime") LocalTime endTime
    );

    // Query to find all pending reservations
    @Query("SELECT r FROM Reservation r WHERE r.status = 'PENDING'")
    List<Reservation> findAllPendingReservations();
}
