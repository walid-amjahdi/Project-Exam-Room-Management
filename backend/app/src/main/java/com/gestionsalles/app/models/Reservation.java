package com.gestionsalles.app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date reservationDate;
    private String status;

    public void createReservation(Date reservationDate, String status) {

    }
    public void updateReservation() {


    }
    public void cancelReservation() {


    }
}
