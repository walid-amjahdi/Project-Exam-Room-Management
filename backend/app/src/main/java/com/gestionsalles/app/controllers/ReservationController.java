package com.gestionsalles.app.controllers;

import com.gestionsalles.app.models.Reservation;
import com.gestionsalles.app.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {
    private final ReservationService reservationServ;

    @GetMapping
    public ResponseEntity<List<Reservation>> findAllReservations(){
        List<Reservation> reservations= reservationServ.findAllReservations();
        if(reservations.isEmpty()){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Reservation>> findPendingReservations(){
        List<Reservation> reservations= reservationServ.findAllPendingReservations();
        if(reservations.isEmpty()){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> findReservation(@PathVariable Long id){
        Optional<Reservation> r= reservationServ.findReservationById(id);

        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<Reservation>> findByTeacherEmail(@RequestParam String email){
        List<Reservation> r= reservationServ.findByTeacherEmail(email);

        if(r != null && !r.isEmpty()){
            return ResponseEntity.ok(r);
        }
        return ResponseEntity.ok(List.of());
    }
    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<Reservation>> findById(@PathVariable Long id){
        List<Reservation> reservations= reservationServ.findByTeacherId(id);
        if(!reservations.isEmpty()){
            return ResponseEntity.ok(reservations);
        }
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Reservation>> findByAdminEmail(@RequestParam String email){
        List<Reservation> reservations = reservationServ.findReservationByAdminEmail(email);
        if(!reservations.isEmpty()){
            return ResponseEntity.ok(reservations);
        }

        return ResponseEntity.ok(List.of());

    }
    @GetMapping("/admin/{id}")
    public ResponseEntity<List<Reservation>> findByAdminId(@PathVariable Long id){
        List<Reservation> reservations=reservationServ.findReservationByAdminId(id);

        if(!reservations.isEmpty()){
            return ResponseEntity.ok(reservations);
        }

        return ResponseEntity.ok(List.of());

    }
    @GetMapping("/filter")
    public ResponseEntity<List<Reservation>> filterReservations(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String roomName){
        if(date != null && !date.isEmpty() && roomName != null && !roomName.isEmpty()){
            return ResponseEntity.ok(reservationServ.findByDateAndRoomName(LocalDate.parse(date), roomName));
        } else if(date != null && !date.isEmpty()){
            return ResponseEntity.ok(reservationServ.findByDate(LocalDate.parse(date)));
        } else if(roomName != null && !roomName.isEmpty()){
            return ResponseEntity.ok(reservationServ.findReservationByRoomName(roomName));
        }
        return ResponseEntity.ok(reservationServ.findAllReservations());
    }

    @GetMapping("/room")
    public ResponseEntity<List<Reservation>> findByRoomName(@RequestParam String name){
        List<Reservation> reservations= reservationServ.findReservationByRoomName(name);

        if(!reservations.isEmpty()){
            return ResponseEntity.ok(reservations);
        }

        return ResponseEntity.ok(List.of());

    }
    @GetMapping("/room/{id}")
    public ResponseEntity<List<Reservation>> findByRoomId(@PathVariable Long id){
        List<Reservation> reservations=reservationServ.findReservationByRoomId(id);

        if(!reservations.isEmpty()){
            return ResponseEntity.ok(reservations);
        }

        return ResponseEntity.ok(List.of());

    }


    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation){
        Optional<Reservation> r=reservationServ.addReservation(reservation);
        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Reservation> addReservationLegacy(@RequestBody Reservation reservation){
        Optional<Reservation> r=reservationServ.addReservation(reservation);
        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,@RequestBody Reservation reservation){
        Optional<Reservation> r= reservationServ.updateReservationById(id,reservation);

        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservationSimple(@PathVariable Long id,@RequestBody Reservation reservation){
        Optional<Reservation> r= reservationServ.updateReservationById(id,reservation);
        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<Reservation> approveReservation(@PathVariable Long id){
        Optional<Reservation> reservation = reservationServ.findReservationById(id);
        if(reservation.isPresent()){
            reservation.get().setStatus(com.gestionsalles.app.models.ReservationStatus.CONFIRMED);
            Optional<Reservation> updated = reservationServ.updateReservationById(id, reservation.get());
            if(updated.isPresent()){
                return ResponseEntity.ok(updated.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Reservation> rejectReservation(@PathVariable Long id){
        Optional<Reservation> reservation = reservationServ.findReservationById(id);
        if(reservation.isPresent()){
            reservation.get().setStatus(com.gestionsalles.app.models.ReservationStatus.REJECTED);
            Optional<Reservation> updated = reservationServ.updateReservationById(id, reservation.get());
            if(updated.isPresent()){
                return ResponseEntity.ok(updated.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id){
        Optional<Reservation> r= reservationServ.deleteReservationById(id);

        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservationSimple(@PathVariable Long id){
        Optional<Reservation> r= reservationServ.deleteReservationById(id);
        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
        }
        return ResponseEntity.notFound().build();
    }



}
