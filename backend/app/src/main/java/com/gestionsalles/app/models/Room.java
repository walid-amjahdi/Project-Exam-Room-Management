package com.gestionsalles.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name="rooms")
public class Room {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="room_id")
    private Long id;

    @Column
    private String name;
    private int capacity;
    private String building;
    private String location;
    private Boolean available;

    @ManyToOne
    @JoinColumn(name="room_admin")
    private Admin admin;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    @ToString.Exclude
    private List<Reservation> room_reservations = new ArrayList<>();




}
