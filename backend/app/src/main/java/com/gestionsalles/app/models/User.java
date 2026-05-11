package com.gestionsalles.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String name;
    private String email;
    private String password;

    @Enumerated(value =  EnumType.STRING)
    private Role role;

    public String login(){
        return null;
    }
    public String logout(){
        return null;
    }
}

