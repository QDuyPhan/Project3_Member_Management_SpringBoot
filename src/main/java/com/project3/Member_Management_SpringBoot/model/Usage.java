package com.project3.Member_Management_SpringBoot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thongtinsd")
public class Usage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTT")
    private Integer id;

    @Column(name = "tgvao", nullable = true)
    private Timestamp enteredTime;

    @Column(name = "tgmuon", nullable = true)
    private Timestamp borrowedTime;

    @Column(name = "tgtra", nullable = true)
    private Timestamp paidTime;

    @Column(name = "tgdatcho", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp reserveTime;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "MaTV")
    private Member member;

    //@ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne
    @JoinColumn(name = "MaTB", nullable = true)
    private Device device;
}
