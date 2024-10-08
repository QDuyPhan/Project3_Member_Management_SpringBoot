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

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thanhvien")
public class Member {

    @Id
    @Column(name = "MaTV")
    private Integer id;

    @Column(name = "Hoten")
    private String name;

    @Column(name = "Khoa")
    private String department;

    @Column(name = "Nganh")
    private String major;

    @Column(name = "SDT")
    private String phone;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Discipline> disciplines;

    //@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "member", fetch = FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member",fetch = FetchType.LAZY)
    private List<Usage> usages;

}
