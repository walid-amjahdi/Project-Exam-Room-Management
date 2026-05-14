package com.gestionsalles.app.models;

import jakarta.persistence.*;
import lombok.*;

//
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@MappedSuperclass
@Getter
@Setter
@Table(name="app_users")
public abstract class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String email;

    private boolean enabled = true;

}
