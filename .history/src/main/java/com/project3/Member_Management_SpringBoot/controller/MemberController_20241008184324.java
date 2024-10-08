package com.project3.Member_Management_SpringBoot.controller;

import com.project3.Member_Management_SpringBoot.annotation.AuthRequire;
import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.service.DeviceService;
import com.project3.Member_Management_SpringBoot.service.DisciplineService;
import com.project3.Member_Management_SpringBoot.service.MemberService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/admin/membersList")
    @AuthRequire
    public String getAllMembers(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "") String query) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Member> members = memberService.findAllByNameOrID(query, pageable);
        model.addAttribute("members", members.getContent());
        model.addAttribute("size", members.getSize());
        model.addAttribute("totalPages", members.getTotalPages());
        model.addAttribute("totalElements", members.getTotalElements());
        model.addAttribute("currentPage", page); // Add current page number
        model.addAttribute("query", query); // Add query for search persistence
        return "admin/membersList";
    }

    @GetMapping("/admin/membersList/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Member member = new Member();
        theModel.addAttribute("member", member);
        return "admin/memberForm";
    }

    @GetMapping("/admin/membersList/showFormForUpdate")
    @AuthRequire
    public String showFormForUpdate(@RequestParam("memberId") Integer ID, Model theModel) {
        Member member = memberService.findById(ID);
        theModel.addAttribute("member", member);
        return "admin/memberForm";
    }

    @PostMapping("/admin/membersList/saveMember")
    public String saveMember(@ModelAttribute("member") Member member) {
        memberService.saveMember(member);
        return "redirect:/admin/membersList";
    }

    @GetMapping("/admin/membersList/deleteMember")
    @AuthRequire
    public String deleteMember(@RequestParam("memberId") Integer ID) {
        memberService.deleteById(ID);
        return "redirect:/admin/membersList";
    }

    @PostMapping("/admin/membersList/deleteByYear")
    public String deleteMembersByYear(@RequestParam("activeYear") String activeYear) {
        System.out.println(activeYear);
        memberService.deleteByActiveYear(activeYear);
        return "redirect:/admin/membersList";
    }

    @PostMapping("/admin/studyAreaManagement/checkID")
    public String checkID(@ModelAttribute("member") Member member, Model theModel) {
        Integer memberID = member.getId();
        if (!memberService.checkUserExists(memberID)) {
            return "redirect:/admin/studyAreaManagement?IDNotExist";
        }
        Member checkedMember = memberService.findById(memberID);
        theModel.addAttribute("member", checkedMember);
        return "admin/studyArea";
    }

}
