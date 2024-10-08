package com.project3.Member_Management_SpringBoot.controller;

import com.project3.Member_Management_SpringBoot.annotation.AuthRequire;
import com.project3.Member_Management_SpringBoot.annotation.RoleRequire;
import com.project3.Member_Management_SpringBoot.model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    @AuthRequire
    public String home(Model theModel) {
        return "users/profile";
    }
    
    @GetMapping("/admin/studyAreaManagement")
    @AuthRequire
    @RoleRequire({"admin"})
    public String dashboard(Model theModel) {
        Member member = new Member();
        theModel.addAttribute("member", member);
        return "admin/studyArea";
    }
}

