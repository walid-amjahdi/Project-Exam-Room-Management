package com.example.demo.controllers;

import com.example.demo.models.Reservation;
import com.example.demo.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
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

        if(!r.isEmpty()){
            return ResponseEntity.ok(r);
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<Reservation>> findById(@PathVariable Long id){
        List<Reservation> reservations= reservationServ.findByTeacherId(id);
        if(!reservations.isEmpty()){
            return ResponseEntity.ok(reservations);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Reservation>> findByAdminEmail(@RequestParam String email){
        List<Reservation> reservations = reservationServ.findReservationByAdminEmail(email);
        if(!reservations.isEmpty()){
            return ResponseEntity.ok(reservations);
        }

        return ResponseEntity.badRequest().build();

    }
    @GetMapping("/admin/{id}")
    public ResponseEntity<List<Reservation>> findByAdminId(@PathVariable Long id){
        List<Reservation> reservations=reservationServ.findReservationByAdminId(id);

        if(!reservations.isEmpty()){
            return ResponseEntity.ok(reservations);
        }

        return ResponseEntity.badRequest().build();

    }
    @GetMapping("/room")
    public ResponseEntity<List<Reservation>> findByRoomName(@RequestParam String name){
        List<Reservation> reservations= reservationServ.findReservationByRoomName(name);

        if(!reservations.isEmpty()){
            return ResponseEntity.ok(reservations);
        }

        return ResponseEntity.badRequest().build();

    }
    @GetMapping("/room/{id}")
    public ResponseEntity<List<Reservation>> findByRoomId(@PathVariable Long id){
        List<Reservation> reservations=reservationServ.findReservationByRoomId(id);

        if(!reservations.isEmpty()){
            return ResponseEntity.ok(reservations);
        }

        return ResponseEntity.badRequest().build();

    }


    @PostMapping("/add")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation){
        Optional<Reservation> r=reservationServ.addReservation(reservation);
        if(r.isPresent()){
            return ResponseEntity.ok(reservationServ.findReservationById(r.get().getId()).get());
        }
        return ResponseEntity.ok(reservationServ.findReservationByDate(reservation.getReservationDate()).get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,@RequestBody Reservation reservation){
        Optional<Reservation> r= reservationServ.updateReservationById(id,reservation);


        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
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



}
