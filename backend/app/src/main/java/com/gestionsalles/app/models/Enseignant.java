package com.gestionsalles.app.models;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("Enseignant_table")
public class Enseignant extends User {
    private String departement;
    private String grade;

    public Enseignant(String departement, String grade) {
        this.departement = departement;
        this.grade = grade;
    }

    public Enseignant(Long id, String name, String email, String password, Role role, String departement, String grade) {
        super(id, name, email, password, role);
        this.departement = departement;
        this.grade = grade;
    }

    public void getReservation(){

    }
}
