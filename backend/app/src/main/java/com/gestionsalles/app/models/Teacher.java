package com.gestionsalles.app.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="teachers")
@PrimaryKeyJoinColumn(name="teacher_id",foreignKey = @ForeignKey(name="fk_teacher_user"))
public class Teacher extends User {

    private String department;

    private String grade;

    @Column(unique = true)
    private String phoneNumber;


    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    @ToString.Exclude
    private List<Reservation> teacher_reservations= new ArrayList<>();






}
