package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by aliatakan on 16/11/17.
 */
@Entity
@Table(name = "application")
public @Data class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "classid")
    private Integer classId;

    @Column(name = "studentid")
    private Long studentId;

    @Column(name = "status")
    private String status;


}
