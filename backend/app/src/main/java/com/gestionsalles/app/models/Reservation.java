package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name="reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private Long id;

    @Column(unique = true)
    private Date reservationDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private String reason;


    @ManyToOne
    @JoinColumn(name="reservation_teacher")
    private Teacher reservation_teacher;

    @ManyToOne
    @JoinColumn(name="reservation_admin")
    private Admin reservation_admin;

    @ManyToOne
    @JoinColumn(name="reservation_room")
    private Room reservation_room;

}
