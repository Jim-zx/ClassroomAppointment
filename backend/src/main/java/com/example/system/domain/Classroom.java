package com.example.system.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="classroom")
public class Classroom {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter @Getter
    @Column(name = "tower", nullable = false, length = 100)
    private String tower;

    @Setter @Getter
    @Column(name = "num", nullable = false, length = 100)
    private String num;

    @Setter @Getter
    @Column(name = "type", nullable = false, length = 100)
    private String type;
}
