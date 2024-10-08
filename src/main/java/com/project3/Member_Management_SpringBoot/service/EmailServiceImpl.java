/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;

import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.model.PasswordResetToken;
import com.project3.Member_Management_SpringBoot.repository.TokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author domin
 *
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private TokenRepository tokenRepository;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Value("${spring.mail.password}")
    private String password;

    @Override
    public String sendEmail(Member member) {
        try {
            String resetLink = generateResetToken(member);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(fromEmail);// input the senders email ID
            msg.setTo(member.getEmail());

            msg.setSubject("Welcome To My Channel");
            msg.setText("Hello \n\n" + "Please click on this link to Reset your Password :" + resetLink + ". \n\n"
                    + "Regards \n" + "ABC");

            javaMailSender.send(msg);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }
    
    public String generateResetToken(Member user) {
	UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setMember(user);
        resetToken.setToken(uuid.toString());
        resetToken.setExpiryDateTime(expiryDateTime);
        resetToken.setMember(user);
        PasswordResetToken token = tokenRepository.save(resetToken);
        if (token != null) {
            String endpointUrl = "http://localhost:8080/resetPassword";
            return endpointUrl + "/" + resetToken.getToken();
        }
        return "";
    }
    

    @Override
    public boolean hasExpired(LocalDateTime expirDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expirDateTime.isAfter(currentDateTime);
    }
    
}
