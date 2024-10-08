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
@Table(name = "thietbi")
public class Device {
    @Id
    @Column(name = "MaTB")
    private Integer id;

    @Column(name = "TenTB")
    private String name;

    @Column(name = "MotaTB")
    private String description;

    //@OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "device",cascade = CascadeType.ALL)
    private List<Usage> usages;
}
