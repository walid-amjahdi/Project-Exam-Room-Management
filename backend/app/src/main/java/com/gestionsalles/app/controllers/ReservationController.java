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

    @PostMapping("/add")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation){
        Optional<Reservation> r=reservationServ.addReservation(reservation);
        if(r.isPresent()){
            return ResponseEntity.ok(r.get());
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
