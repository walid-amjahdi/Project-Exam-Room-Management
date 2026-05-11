package com.gestionsalles.app.models;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("Admin_table")
public class Admin extends User {

    @Column(name="Manage Users", nullable=false)
    private boolean canManageUsers;

    @Column(name="Manage Rooms",nullable = false)
    private boolean canManageRooms;


    public void manageUsers() {

    }

    public void manageRooms() {

    }

    public void confirmReservation(){

    }
}
