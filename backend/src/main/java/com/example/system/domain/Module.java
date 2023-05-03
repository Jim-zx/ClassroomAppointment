package com.example.system.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="module")
public class Module {
    @Id @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter @Getter
    @Column(name = "moduleName", nullable = false, length = 100)
    private String moduleName;

    @Setter @Getter
    @Column(name = "route", nullable = false, length = 100)
    private String route;

    @Setter @Getter
    @Column(name = "authority", nullable = false, length = 100)
    private String authority;

    @Setter @Getter
    @Column(name = "color", nullable = false, length = 100)
    private String color;

    @Setter @Getter
    @Column(name = "icon", nullable = false, length = 100)
    private String icon;
}
