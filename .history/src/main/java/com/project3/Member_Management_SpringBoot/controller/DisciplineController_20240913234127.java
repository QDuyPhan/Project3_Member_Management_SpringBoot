package com.project3.Member_Management_SpringBoot.controller;

import com.project3.Member_Management_SpringBoot.annotation.RoleRequire;
import com.project3.Member_Management_SpringBoot.model.Discipline;
import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.service.DisciplineService;
import com.project3.Member_Management_SpringBoot.service.MemberService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;
    private final MemberService memberService;

    @GetMapping("/admin/disciplineList")
    // @RoleRequire({ "admin" })
    public String getAllDiscipline(Model model) {
        Iterable<Discipline> disciplineList = disciplineService.getAllDiscipline();
        model.addAttribute("disciplineList", disciplineList);
        return "admin/disciplineAdmin";
    }

    @GetMapping("/admin/disciplineList/addDiscipline")
    // @RoleRequire({ "admin" })
    public String addDiscipline(Model model) {
        Iterable<Member> members = memberService.getAllMembers();
        model.addAttribute("memberList", members);
        model.addAttribute("discipline", new Discipline());
        return "admin/addDiscipline";
    }

    @RequestMapping(value = "admin/disciplineList/save", method = RequestMethod.POST)
    public String save(Discipline discipline) {
        disciplineService.saveDiscipline(discipline);
        return "redirect:/admin/disciplineList";
    }

    @GetMapping("/admin/disciplineList/editDisciplineForm")
    // @RoleRequire({ "admin" })
    public String editDisciplineForm(@RequestParam("id") Integer id, Model model) {
        Iterable<Member> members = memberService.getAllMembers();
        model.addAttribute("memberList", members);

        Discipline discipline = disciplineService.findById(id);
        if (discipline.getStatus() == 1) {
            model.addAttribute("discipline", discipline);
        } else {
            String error = "Updating discipline FAILS because the status is 'Da xu ly'";
            return "redirect:/admin/disciplineList?err=" + error;
        }

        return "admin/editDisciplineForm";
    }

    @PostMapping("/admin/disciplineList/editDiscipline")
    public String editDisciplineSubmit(@RequestParam("id") int id, @RequestParam("description") String description,
            @RequestParam("fine") String fine, @RequestParam("status") int status) {
        Discipline discipline = disciplineService.findById(id);
        if (discipline != null) {
            discipline.setDescription(description);
            if (fine != null && !fine.trim().isEmpty()) {
                discipline.setFine(Integer.valueOf(fine.trim()));
            } else {
                discipline.setFine(null);
            }
            discipline.setStatus(status);
            disciplineService.saveDiscipline(discipline);
        }
        String success = "Update discipline successfully";
        return "redirect:/admin/disciplineList?success=" + success;
    }

    @GetMapping("/deleteDiscipline")
    public String deleteDiscipline(@RequestParam("id") int id) {
        try {
            Discipline discipline = disciplineService.findById(id);
            if (discipline.getStatus() == 1) {
                disciplineService.deleteById(id);
                String success = "Delete discipline successfully";
                return "redirect:/admin/disciplineList?success=" + success;
            } else {
                String err = "Delete discipline FAILS because the status is 'Da xu ly'";
                return "redirect:/admin/disciplineList?err=" + err;
            }
        } catch (Exception e) {
            String err = "Delete discipline ERROR";
            return "redirect:/admin/disciplineList?err=" + err;
        }

    }
}
