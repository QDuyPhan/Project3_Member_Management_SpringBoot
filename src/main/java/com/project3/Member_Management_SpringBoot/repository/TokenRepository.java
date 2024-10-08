package com.project3.Member_Management_SpringBoot.repository;


import com.project3.Member_Management_SpringBoot.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author domin
 */
@Repository
public interface TokenRepository extends JpaRepository<PasswordResetToken, Integer>{
    
    PasswordResetToken findByToken(String token);
    
}
