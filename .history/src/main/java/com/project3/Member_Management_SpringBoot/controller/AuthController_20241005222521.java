package com.project3.Member_Management_SpringBoot.controller;

import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.model.PasswordResetToken;
import com.project3.Member_Management_SpringBoot.repository.MemberRepository;
import com.project3.Member_Management_SpringBoot.repository.TokenRepository;
import com.project3.Member_Management_SpringBoot.service.EmailService;
import com.project3.Member_Management_SpringBoot.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static final String PASSWORD_REGEX = "^.{1,10}$";
    private static final String PHONE_REGEX = "^0\\d{9}$";

    @GetMapping("/login")
    public String login(Model theModel) {
        Member member = new Member();
        theModel.addAttribute("member", member);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("member") Member loginMember, HttpSession session, Model theModel) {
        Integer memberId = 0;
        try {
            memberId = loginMember.getId();
        } catch (NumberFormatException e) {
            return "redirect:/login?error=ID is not correct";
        }
        String password = loginMember.getPassword();
        if (!memberService.checkUserExists(memberId)) // kiểm tra ID có tồn tại không
        {
            return "redirect:/login?error=ID is not correct";
        }
        Member member = memberService.findById(memberId);
        if (!memberService.checkPasswordUser(member, password)) {
            return "redirect:/login?error=password is not correct";
        }
        session.setAttribute("user", member);

        // FIXME: role should be retrieved from database
        String role = "user";
        if (memberId == 123456) {
            role = "admin";
        }
        session.setAttribute("role", role);
        if (role.equals("admin")) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model theModel) {
        Member member = new Member();
        theModel.addAttribute("member", member);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("member") Member member, Model model) {
        if (memberRepository.existsById(member.getId())) {
            // return "redirect:/register?errorid=ID is exists";
            model.addAttribute("errorID", "ID is exists");
        }
        // if (member.getId().equals(null) || member.getId().equals(0)) {
        // return "redirect:/register?error=ID is not empty";
        // }
        if (member.getName().isEmpty()) {
            // return "redirect:/register?errorname=Name is not empty";
            model.addAttribute("errorname", "Name is not empty");
        }
        memberService.saveMember(member);
        return "redirect:/login?successSignUp";
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    private boolean isValidePhone(String phone) {
        return phone.matches(PHONE_REGEX);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "users/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@ModelAttribute Member memberDTO) {
        try {
            String output = "";
            System.out.println(memberDTO.getEmail());
            Member user = memberService.findByEmail(memberDTO.getEmail());
            if (user != null) {
                output = emailService.sendEmail(user);
            }
            if (output.equals("success"))
                return "redirect:/forgotPassword?success&email=" + memberDTO.getEmail();
            else {
                return "redirect:/login?error";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/login?error";
        }
    }

    @GetMapping("/resetPassword/{token}")
    public String resetPasswordForm(@PathVariable String token, Model model) {
        PasswordResetToken reset = tokenRepository.findByToken(token);
        if (reset != null && emailService.hasExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getMember().getEmail());
            return "users/resetPassword";
        }
        return "redirect:/forgotPassword?error";
    }

    @PostMapping("/resetPassword")
    public String passwordResetProcess(@ModelAttribute Member memberDTO) {
        Member member = memberService.findByEmail(memberDTO.getEmail());
        if (member != null) {
            member.setPassword(memberDTO.getPassword());
            memberService.saveMember(member);
        }
        return "login";
    }

}
