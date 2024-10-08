package com.project3.Member_Management_SpringBoot.controller;

import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.model.PasswordResetToken;
import com.project3.Member_Management_SpringBoot.repository.MemberRepository;
import com.project3.Member_Management_SpringBoot.repository.TokenRepository;
import com.project3.Member_Management_SpringBoot.service.EmailService;
import com.project3.Member_Management_SpringBoot.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/login")
    public String login(Model theModel) {
        Member member = new Member();
        theModel.addAttribute("member", member);
        return "login";
    }

    // @PostMapping("/login")
    // public String login(@ModelAttribute("member") Member loginMember, HttpSession
    // session, Model theModel) {
    // Integer memberId = 0;
    // try {
    // memberId = loginMember.getId();
    // } catch (NumberFormatException e) {
    // return "redirect:/login?error=ID is not correct";
    // }
    // String password = loginMember.getPassword();
    // if (!memberService.checkUserExists(memberId)) // kiểm tra ID có tồn tại không
    // {
    // return "redirect:/login?error=ID is not correct";
    // }
    // Member member = memberService.findById(memberId);
    // if (!memberService.checkPasswordUser(member, password)) {
    // return "redirect:/login?error=password is not correct";
    // }
    // session.setAttribute("user", member);

    // // FIXME: role should be retrieved from database
    // String role = "user";
    // if (memberId == 123456) {
    // role = "admin";
    // }
    // session.setAttribute("role", role);
    // if (role.equals("admin")) {
    // return "redirect:/admin/dashboard";
    // }
    // return "redirect:/";
    // }
    @PostMapping("/login")
    public String loginCombined(@RequestParam(required = false) Integer MaTV,
            @RequestParam(required = false) String Password, HttpServletResponse response,
            HttpSession session, RedirectAttributes redirectAttributes) {
        if (MaTV != null && Password != null) {
            Optional<Member> optionalMember = memberRepository.findOneByIdAndPassword(MaTV, Password);
            if (optionalMember.isPresent()) {
                Member member = optionalMember.get();
                String hoten = member.getName();
                String maTV = String.valueOf(member.getId());
                session.setAttribute("loggedInMaTV", MaTV);
                session.setAttribute("loggedInHoTen", hoten);

                try {
                    String encodedValue = URLEncoder.encode(hoten, "UTF-8");
                    String encodedMaTV = URLEncoder.encode(maTV, "UTF-8");
                    Cookie cookie = new Cookie("hoten", encodedValue);
                    Cookie cookieMaTV = new Cookie("MaTV", encodedMaTV);
                    cookie.setMaxAge(3600);
                    response.addCookie(cookie);
                    response.addCookie(cookieMaTV);
                } catch (UnsupportedEncodingException e) {
                    // Handle encoding exception
                    e.printStackTrace();
                }

                redirectAttributes.addFlashAttribute("member", member);

                // Role logic - FIXME: Retrieve role from database
                String role = "user";
                if (MaTV == 123456) {
                    role = "admin";
                }
                session.setAttribute("role", role);

                return role.equals("admin") ? "redirect:/admin/dashboard" : "redirect:/datcho";
            }
        }

        return "redirect:/login?error";
    }

    @GetMapping("/register")
    public String register(Model theModel) {
        Member member = new Member();
        theModel.addAttribute("member", member);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("member") Member member) {
        memberService.saveMember(member);
        return "redirect:/login?successSignUp";
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
