/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.model;

/**
 *
 * @author buing
 */
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "xuly")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaXL")
    private Integer id;

    @Column(name = "HinhthucXL")
    private String description;

    @Column(name = "Sotien",nullable = true)
    private Integer fine;
    //
    @CreationTimestamp
    @Column(name = "NgayXL",nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp date;

    @Column(name = "TrangthaiXL")
    private Integer status;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "MaTV")
    private Member member;
}