package com.gestionsalles.app.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private String reason;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="session_id")
    private MakeupSession makeupSession;

    @ManyToOne
    @JoinColumn(name="reservation_teacher")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name="reservation_admin")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name="reservation_room")
    private Room room;

}
