package com.project3.Member_Management_SpringBoot.controller;

import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.repository.MemberRepository;
import com.project3.Member_Management_SpringBoot.repository.TokenRepository;
import com.project3.Member_Management_SpringBoot.service.EmailService;
import com.project3.Member_Management_SpringBoot.service.MemberNotFoundException;
import com.project3.Member_Management_SpringBoot.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.bytebuddy.utility.RandomString;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String login(@ModelAttribute("member") Member loginMember, HttpSession session,
            Model theModel) {
        Integer memberId = 0;
        try {
            memberId = loginMember.getId();
            session.setAttribute("MaTV", memberId);
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
        try {
            if (memberRepository.existsById(member.getId()))
                return "redirect:/register?error=ID already exists";

            if (member.getName().isEmpty())
                return "redirect:/register?error=Name is not empty";

            if (!isValidePhone(member.getPhone()))
                return "redirect:/register?error=Phone is not valid";

            if (memberRepository.existsByPhone(member.getPhone()))
                return "redirect:/register?error=Phone already exists";

            if (memberRepository.existsByEmail(member.getEmail()))
                return "redirect:/register?error=Email already exists";

            if (!isValidPassword(member.getPassword()))
                return "redirect:/register?error=Password is not valid";
            memberService.saveMember(member);
            return "redirect:/login?successSignUp";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/register?error=Register is something wrong";
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

    // @GetMapping("/forgotPassword")
    // public String forgotPassword() {
    // return "users/forgotPassword";
    // }

    // @PostMapping("/forgotPassword")
    // public String forgotPassword(@ModelAttribute Member memberDTO) {
    // try {
    // String output = "";
    // System.out.println(memberDTO.getEmail());
    // Member user = memberService.findByEmail(memberDTO.getEmail());
    // if (user != null) {
    // output = emailService.sendEmail(user);
    // }
    // if (output.equals("success"))
    // return "redirect:/forgotPassword?success&email=" + memberDTO.getEmail();
    // else {
    // return "redirect:/login?error";
    // }
    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // return "redirect:/login?error";
    // }
    // }

    // @GetMapping("/resetPassword/{token}")
    // public String resetPasswordForm(@PathVariable String token, Model model) {
    // PasswordResetToken reset = tokenRepository.findByToken(token);
    // if (reset != null && emailService.hasExpired(reset.getExpiryDateTime())) {
    // model.addAttribute("email", reset.getMember().getEmail());
    // return "users/resetPassword";
    // }
    // return "redirect:/forgotPassword?error";
    // }

    // @PostMapping("/resetPassword")
    // public String passwordResetProcess(@ModelAttribute Member memberDTO) {
    // Member member = memberService.findByEmail(memberDTO.getEmail());
    // if (member != null) {
    // member.setPassword(memberDTO.getPassword());
    // memberService.saveMember(member);
    // }
    // return "login";
    // }
    // trang quên mật khẩu
    @GetMapping(value = { "/forgotPassword" })
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
        return "users/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPasswordForm(HttpServletRequest request, Model model) {
        String email = request.getParameter("Email");
        String token = RandomString.make(45);
        try {
            memberService.updateResetPasswordToken(token, email);

            String resetPasswordLink = memberService.getSiteURL(request) +
                    "/resetPassword?token=" + token;
            memberService.sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "we have sent a reset password link to your email.");
        } catch (MemberNotFoundException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error while sending email.");
        }
        model.addAttribute("pageTitle", "Forgot Password");
        return "users/forgotPassword";
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm(@Param("token") String token, Model model) {
        Member member = memberService.get(token);
        if (member == null) {
            model.addAttribute("error", "Invalid token");
            return "error";
        }
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reset your Password");
        return "users/resetPassword";
    }

    @PostMapping("/resetPassword")
    public String processResetPasswordForm(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("newPassword");
        Member member = memberService.get(token);
        if (member == null) {
            model.addAttribute("error", "Invalid token");
            return "error";
        } else {
            memberService.updatePassword(member, password);
            model.addAttribute("message", "Change password successfully");
        }
        return "message";
    }
}
