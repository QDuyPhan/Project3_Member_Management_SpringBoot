package com.project3.Member_Management_SpringBoot.controller.user;

import com.project3.Member_Management_SpringBoot.annotation.AuthRequire;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.project3.Member_Management_SpringBoot.model.Discipline;
import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.model.Usage;
import com.project3.Member_Management_SpringBoot.repository.MemberRepository;
import com.project3.Member_Management_SpringBoot.service.DisciplineService;
import com.project3.Member_Management_SpringBoot.service.EmailService;
import com.project3.Member_Management_SpringBoot.service.MemberService;
import com.project3.Member_Management_SpringBoot.service.UsageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private DisciplineService disciplineService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UsageService usageService;

    @GetMapping("/changePassword")
    public String changePassword() {
        return "users/changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(HttpSession session,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("conformPassword") String confirmNewPassword) {

        int MaTV = (int) session.getAttribute("MaTV");
        if (!newPassword.equals(confirmNewPassword)) {
            return "redirect:/changePassword?error= Conform Password not equal to new password";
        }

        if (MaTV != 0) {
            boolean passwordChanged = memberService.changePassword(MaTV, oldPassword, newPassword);
            if (passwordChanged) {
                return "redirect:/?changePassword?success= Change Password successfully";
            } else {
                return "redirect:/changePassword?error= Old Password wrong";
            }
        }

        // Member member = (Member) session.getAttribute("user");
        // if (!memberService.checkPasswordUser(member, oldPassword)) {
        // return "redirect:/?wrongOldPassword";
        // }
        // member.setPassword(newPassword);
        // memberService.saveMember(member);
        // return "redirect:/?changePasswordSuccess";
        return "users/changePassword";
    }

    // @GetMapping("/changePassword")
    // public String changePassword(Model model, @RequestParam("MaTV") String maTV)
    // {
    // int id = Integer.parseInt(maTV);

    // Member member = memberRepository.findById(id).orElse(null);

    // model.addAttribute("member", member);
    // return "users/changePassword";
    // }

    @PostMapping(value = "/send-email")
    public void sendEmail(@RequestBody Member member) throws Exception {
        try {
            emailService.sendEmail(member);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @GetMapping("/violation_history")
    @AuthRequire
    public String violationHistory(HttpSession session, Model theModel) {
        Member member = (Member) session.getAttribute("user");
        List<Discipline> disciplinesByUser = disciplineService.findByMember(member);
        theModel.addAttribute("data", disciplinesByUser);
        return "users/violationHistory";
    }

    @GetMapping("/borrowedEquipment")
    @AuthRequire
    @Transactional
    public String getBorrowedEquipment(Model model, HttpSession session) throws IOException {
        Member member = (Member) session.getAttribute("user");
        if (member == null) {
            return "error";
        }
        // Iterable<Usage> borrowedDevices = member.getUsages();
        Iterable<Usage> borrowedDevices = usageService.findByMemberIdAndBorrowedTimeNotNull(member.getId());
        model.addAttribute("borrowedDevices", borrowedDevices);
        return "users/detail-borrowed-device";
    }

    @GetMapping("/reservedDevicesList")
    @AuthRequire
    @Transactional
    public String getReservedDevices(Model model, HttpSession session) throws IOException {
        Member member = (Member) session.getAttribute("user");
        if (member == null) {
            return "error";
        }
        List<Usage> reservedDevices = usageService.findByMemberIdAndReserveTimeNotNull(member.getId());
        model.addAttribute("usages", reservedDevices);
        model.addAttribute("member", member);
        return "users/reservedDevicesList";
    }

}
