package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="admins")
@PrimaryKeyJoinColumn(name="admin_id",foreignKey = @ForeignKey(name="fk_admin_user"))
public class Admin extends User{

    private Boolean isSudo;

    @OneToMany(mappedBy = "reservation_admin")
    @ToString.Exclude
    @JsonIgnore
    private List<Reservation> reservations_admin= new ArrayList<>();

    @OneToMany(mappedBy = "room_admin")
    @JsonIgnore
    @ToString.Exclude
    private List<Room> rooms_admin= new ArrayList<>();
}
