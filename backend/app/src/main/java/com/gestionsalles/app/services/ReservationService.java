package com.gestionsalles.app.services;


import com.gestionsalles.app.models.Reservation;
import com.gestionsalles.app.repos.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepo;

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

    public List<Reservation> findAllPendingReservations(){
        return reservationRepo.findAllPendingReservations();
    }

    public List<Reservation> findByDate(LocalDate date){
        return reservationRepo.findByReservationDate(date);
    }

    public List<Reservation> findByDateAndRoomName(LocalDate date, String roomName){
        return reservationRepo.findByReservationDateAndRoom_Name(date, roomName);
    }


    public Optional<Reservation> addReservation(Reservation reservation) {
        // Check for conflicts before saving
        if(checkConflicts(reservation.getRoom().getId(), reservation.getReservationDate(), 
                         reservation.getStartTime(), reservation.getEndTime())){
            return Optional.empty(); // Conflict exists, don't save
        }
        
        reservationRepo.save(reservation);
        return reservationRepo.findById(reservation.getId());
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
        return List.of();
    }


    public List<Reservation> findByTeacherId(Long id) {
        return reservationRepo.findReservationByTeacherId(id);
    }

    /**
     * Check if there are any conflicting reservations for a given room on a specific date/time
     * @param roomId the room ID
     * @param date the reservation date
     * @param startTime the reservation start time
     * @param endTime the reservation end time
     * @return true if conflict exists, false otherwise
     */
    public boolean checkConflicts(Long roomId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Reservation> conflicts = reservationRepo.findConflictingReservations(roomId, date, startTime, endTime);
        return !conflicts.isEmpty();
    }

}
