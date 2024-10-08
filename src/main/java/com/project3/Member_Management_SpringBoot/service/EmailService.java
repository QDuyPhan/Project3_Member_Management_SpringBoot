/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;

import com.project3.Member_Management_SpringBoot.model.Member;
import java.time.LocalDateTime;

public interface EmailService {

    String sendEmail(Member member) throws Exception;
    
    boolean hasExpired(LocalDateTime expirDateTime);
    
}
