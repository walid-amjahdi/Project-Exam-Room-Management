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

    @Column(name="Manage Sales",nullable = false)
    private boolean canManageSales;

    public Admin(Long id, String name, String email, String password, Role role, boolean canManageUsers, boolean canManageSales) {
        super(id, name, email, password, role);
        this.canManageUsers = canManageUsers;
        this.canManageSales = canManageSales;
    }

    public Admin(boolean canManageUsers, boolean canManageSales) {
        super();
        this.canManageUsers = canManageUsers;
        this.canManageSales = canManageSales;
    }



    public void confirmReservation(){

    }
}
