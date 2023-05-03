package com.example.system.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="lesson")
public class Lesson {
    @Id
    @Setter
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter @Getter
    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
