/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;

import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.repository.MemberRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author buing
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Member findById(Integer id) {
        return memberRepository.findById(id).get();
    }

    @Override
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Boolean checkPasswordUser(Member member, String password) {
        return member.getPassword().equals(password);
    }

    @Override
    public Boolean checkUserExists(Integer ID) {
        return memberRepository.findMemberById(ID) != null;
    }

    // @Override
    // public Member findByEmail(String email) {
    // Member optMember = memberRepository.findByEmail(email);
    // return optMember.isPresent() ? optMember.get() : null;
    // }

    @Override
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        memberRepository.findAll().forEach(members::add);
        return members;
    }

    @Override
    public void deleteById(Integer ID) {
        memberRepository.deleteById(ID);
    }

    @Override
    public void deleteByActiveYear(String activeYear) {
        String subID = activeYear.substring(2);
        System.out.println(subID);
        List<Member> memberList = getAllMembers();
        for (Member member : memberList) {
            int memberID = member.getId();
            String memberIDStr = String.valueOf(memberID).substring(1, 3);
            System.out.println(memberIDStr);
            if (subID.equals(memberIDStr)) {
                deleteById(memberID);
            }
        }
    }

    @Override
    public Integer statisticsTotalMember(String department, String major, String startDateStr, String endDateStr) {
        Timestamp startDateTimestamp = null;
        Timestamp endDateTimestamp = null;
        if (startDateStr != null && !startDateStr.isBlank() && !startDateStr.isEmpty()) {
            LocalDate startDateLocal = LocalDate.parse(startDateStr);
            startDateTimestamp = Timestamp.valueOf(startDateLocal.atStartOfDay());
        }
        if (endDateStr != null && !endDateStr.isBlank() && !endDateStr.isEmpty()) {
            LocalDate endDateLocal = LocalDate.parse(endDateStr);
            endDateTimestamp = Timestamp.valueOf(endDateLocal.atStartOfDay());
        }

        return memberRepository.statisticsTotalMember(department, major, startDateTimestamp, endDateTimestamp);
    }

    @Override
    public Page<Member> findAllByNameOrID(String query, Pageable pageable) {
        return memberRepository.findAllByNameOrID(query, pageable);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws MemberNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            member.setResetPasswordToken(token);
            memberRepository.save(member);
        } else {
            throw new MemberNotFoundException("Couldn't not find any member with email " + email);
        }
    }

    @Override
    public Member get(String resetPasswordToken) {
        return memberRepository.findByResetPasswordToken(resetPasswordToken);
    }

    @Override
    public void updatePassword(Member member, String newPassword) {
        member.setPassword(newPassword);
        member.setResetPasswordToken(null);
        memberRepository.save(member);
    }

    @Override
    public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @Override
    public void sendEmail(String email, String resetPasswordLink)
            throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("resetpassword", "Reset Password");
        helper.setTo(email);
        String subject = "Link to reset password";
        String content = "<p>Click the link to change your password: </p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\"> Change my password</a><b></p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public boolean changePassword(int maTV, String currentPassword, String newPassword) {
        Member member = memberRepository.findById(maTV).orElse(null);
        if (member != null && member.getPassword().equals(currentPassword)) {
            member.setPassword(newPassword);
            memberRepository.save(member);
            return true;
        }
        return false;
    }

}
