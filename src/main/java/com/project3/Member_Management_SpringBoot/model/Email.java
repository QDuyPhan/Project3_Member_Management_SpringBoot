/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author domin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String fromAddress;
    private String subject;
    private String body;
}
