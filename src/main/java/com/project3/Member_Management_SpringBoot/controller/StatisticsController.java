/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.controller;

import com.google.gson.Gson;
import com.project3.Member_Management_SpringBoot.annotation.AuthRequire;
import com.project3.Member_Management_SpringBoot.annotation.RoleRequire;
import com.project3.Member_Management_SpringBoot.model.Usage;
import com.project3.Member_Management_SpringBoot.service.DeviceService;
import com.project3.Member_Management_SpringBoot.service.DisciplineService;
import com.project3.Member_Management_SpringBoot.service.MemberService;
import com.project3.Member_Management_SpringBoot.service.UsageService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author buing
 */
@Controller
@RequiredArgsConstructor
public class StatisticsController {

    private final MemberService memberService;
    private final DeviceService deviceService;
    private final DisciplineService disciplineService;
    private final UsageService usageService;

    @GetMapping("/admin/dashboard")
    @AuthRequire
    @RoleRequire({
            "admin"
    })
    public String dashboard(Model theModel) {
        // member statistic
        List<Usage> memberCount = usageService.statisticsMember(null, null, null, null);
        theModel.addAttribute("memberData", memberCount);
        Integer totalMembers = memberService.statisticsTotalMember(null, null, null, null);
        theModel.addAttribute("totalMembers", totalMembers);

        // device statistic
        List<Usage> borrowedDeviceData = usageService.statisticsBorrowedDevice(null, null, null);
        theModel.addAttribute("borrowedDeviceData", borrowedDeviceData);
        Integer totalDevices = deviceService.statisticsTotalBorrowedDevice(null, null, null);
        theModel.addAttribute("totalDevices", totalDevices);

        // discipline statistic
        Integer resolvedDisciplineData = disciplineService.findByStatus(0);
        theModel.addAttribute("resolvedDisciplineData", resolvedDisciplineData);
        Integer totalFineData = disciplineService.findSumFine();
        theModel.addAttribute("totalFineData", totalFineData);
        Integer unresolvedDisciplineData = disciplineService.findByStatus(1);
        theModel.addAttribute("unresolvedDisciplineData", unresolvedDisciplineData);
        return "admin/statistics";
    }

    @PostMapping("/admin/dashboard/statisticDevice")
    @AuthRequire
    @RoleRequire("admin")
    public @ResponseBody String statisticDevice(Model theModel, @RequestParam("search") String name,
            @RequestParam("select") String type, @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        List<Usage> deviceData = new ArrayList<>();
        Integer totalDevices = 0;

        if (type.equals("borrowing")) {
            deviceData = usageService.statisticsBorrowingDevice(startDate, endDate);
            totalDevices = deviceService.statisticsTotalBorrowingDevice(startDate, endDate);
        } else {
            deviceData = usageService.statisticsBorrowedDevice(name, startDate, endDate);
            totalDevices = deviceService.statisticsTotalBorrowedDevice(name, startDate, endDate);
        }

        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        for (Usage usage : deviceData) {
            Integer deviceID = usage.getDevice().getId();
            String deviceName = usage.getDevice().getName();
            String deviceDescription = usage.getDevice().getDescription();
            Timestamp borrowedTime = usage.getBorrowedTime();

            JSONObject item = new JSONObject();
            item.put("deviceID", deviceID);
            item.put("deviceName", deviceName);
            item.put("deviceDescription", deviceDescription);
            item.put("borrowedTime", borrowedTime);

            array.put(item);
        }
        json.put("device", array);
        json.put("total", totalDevices);

        return json.toString();

    }

    @PostMapping("/admin/dashboard/statisticMember")
    @AuthRequire
    @RoleRequire("admin")
    public @ResponseBody String statisticMember(Model theModel, @RequestParam("department") String department,
            @RequestParam("major") String major, @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        List<Usage> memberData = new ArrayList<>();
        Integer totalMember = 0;

        if (department.equals("ALL")) {
            department = null;
        }
        if (major.equals("--Tất cả ngành--")) {
            major = null;
        }

        // member statistic
        memberData = usageService.statisticsMember(department, major, startDate, endDate);

        totalMember = memberService.statisticsTotalMember(department, major, startDate, endDate);

        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        for (Usage usage : memberData) {
            Integer memberID = usage.getMember().getId();
            String memberName = usage.getMember().getName();
            String memberDepartment = usage.getMember().getDepartment();
            String memberMajor = usage.getMember().getMajor();
            Timestamp enteredTime = usage.getEnteredTime();

            JSONObject item = new JSONObject();
            item.put("memberID", memberID);
            item.put("memberName", memberName);
            item.put("memberDepartment", memberDepartment);
            item.put("memberMajor", memberMajor);
            item.put("enteredTime", enteredTime);

            array.put(item);
        }
        json.put("members", array);
        json.put("total", totalMember);

        return json.toString();

    }
}
