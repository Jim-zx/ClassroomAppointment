package com.example.system.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User{


    public User() {
    }

    public User(String username, String email, String password, String authorityId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorityId = authorityId;
    }

    @Id @Setter @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter @Getter
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Setter @Getter
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Setter @Getter
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Setter @Getter
    @Column(name = "authority_id", nullable = false, length = 10)
    private String authorityId;

}