/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.controller;

import com.project3.Member_Management_SpringBoot.annotation.AuthRequire;
import com.project3.Member_Management_SpringBoot.annotation.RoleRequire;
import com.project3.Member_Management_SpringBoot.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author buing
 */
@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/admin/membersList/importExcel")
    @AuthRequire
    @RoleRequire(
            {
                "admin"
            })
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (fileService.hasCSVformat(file))
        {
            fileService.processAndSaveData(file);
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Upload file successfully: " + file.getOriginalFilename()));
            return "redirect:/admin/membersList?importExcelSuccessfully";
        }
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Please upload CVS file!"));
         return "redirect:/admin/membersList?importExcelFailed";
    }
}
