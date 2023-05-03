package com.example.system.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name="active")
public class Active {
    public Active(){}

    @Id
    @Setter
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter @Getter
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Setter @Getter
    @Column(name = "principal_id", nullable = false, length = 100)
    private Long principalId;

    @Setter @Getter
    @Column(name = "create_date", nullable = false, length = 100)
    private Date createDate;

    @Setter @Getter
    @Column(name = "accept", nullable = false, length = 100)
    private Boolean accept;

    @Setter @Getter
    @Column(name = "date", nullable = false, length = 100)
    private Date date;

    @Setter @Getter
    @Column(name = "time", nullable = false, length = 100)
    private ArrayList<Integer> time;

    @Setter @Getter
    @Column(name = "roomId", nullable = false, length = 100)
    private Long roomId;

    public Active(Long id, String name, Long principalId, Date createDate, Boolean accept, Date date, ArrayList<Integer> time) {
        this.id = id;
        this.name = name;
        this.principalId = principalId;
        this.createDate = createDate;
        this.accept = accept;
        this.date = date;
        this.time = time;
    }

}
