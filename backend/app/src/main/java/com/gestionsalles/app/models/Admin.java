package com.gestionsalles.app.models;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("Admin_table")
public class Admin extends User {

    @Column(name="Manage Users", nullable=false)
    private boolean canManageUsers;

    @Column(name="Manage Rooms",nullable = false)
    private boolean canManageRooms;

    public Admin(Long id, String name, String email, String password, Role role, boolean canManageUsers, boolean canManageRooms) {
        super(id, name, email, password, role);
        this.canManageUsers = canManageUsers;
        this.canManageRooms = canManageRooms;
    }

    public Admin(boolean canManageUsers, boolean canManageRooms) {
        super();
        this.canManageUsers = canManageUsers;
        this.canManageRooms = canManageRooms;
    }

    public void manageUsers() {

    }

    public void manageRooms() {

    }

    public void confirmReservation(){

    }
}
