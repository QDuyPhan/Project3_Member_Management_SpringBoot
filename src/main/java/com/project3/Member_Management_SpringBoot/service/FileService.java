/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author buing
 */
public interface FileService {

    Boolean hasCSVformat(MultipartFile file);

    void processAndSaveData(MultipartFile file);
}
