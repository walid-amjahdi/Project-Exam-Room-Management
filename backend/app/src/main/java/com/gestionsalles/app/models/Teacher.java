package com.gestionsalles.app.models;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Enseignant_table")
public class Teacher extends User {
    private String departement;
    private String grade;


    public void requestReservation(){

    }
    public void viewReservations(){

    }
}
