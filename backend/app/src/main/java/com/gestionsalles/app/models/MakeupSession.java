package com.gestionsalles.app.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "makeup_sessions")
public class MakeupSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long id;

    @Column(nullable = false)
    private String moduleName;

    @Column(nullable = false)
    private String studentGroup;

    @Column(nullable = false)
    private String academicYear;
}
