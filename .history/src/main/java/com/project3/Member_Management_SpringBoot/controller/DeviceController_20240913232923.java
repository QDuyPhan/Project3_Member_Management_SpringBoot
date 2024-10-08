package com.project3.Member_Management_SpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project3.Member_Management_SpringBoot.annotation.RoleRequire;
import com.project3.Member_Management_SpringBoot.model.Device;
import com.project3.Member_Management_SpringBoot.model.Usage;
import com.project3.Member_Management_SpringBoot.service.DeviceService;
import com.project3.Member_Management_SpringBoot.service.UsageService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Controller
public class DeviceController {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private UsageService usageService;

    @GetMapping("/admin/device-management")
    @RoleRequire({ "admin" })
    public String deviceManagement(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "") String query) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Device> devicePage = deviceService.findAllByNameOrID(query, pageable);
        model.addAttribute("devices", devicePage.getContent());
        model.addAttribute("size", devicePage.getSize());
        model.addAttribute("totalPages", devicePage.getTotalPages());
        model.addAttribute("totalElements", devicePage.getTotalElements());
        model.addAttribute("currentPage", page); // Add current page number
        model.addAttribute("query", query); // Add query for search persistence
        return "admin/device_management/deviceList";
    }

    @GetMapping("/admin/device-management/add")
    @RoleRequire({ "admin" })
    public String addDeviceForm(Model model) {
        model.addAttribute("device", new Device());
        return "admin/device_management/addDeviceForm";
    }

    @PostMapping("/add-device")
    public String addDeviceSubmit(@RequestParam("id") int id, @RequestParam("name") String name,
            @RequestParam("description") String description) {

        Device existingDeviceById = deviceService.findDeviceById(id);
        if (existingDeviceById != null) {
            String err = "Device ID already exists!";
            return "redirect:/admin/device-management/add?err=" + err;
        }

        Device device = new Device();
        device.setId(id);
        device.setName(name);
        device.setDescription(description);
        deviceService.saveDevice(device);
        deviceService.saveDevice(device);

        String success = "Add device successfully";
        return "redirect:/admin/device-management/add?success=" + success;
    }

    @GetMapping("/admin/device-management/{id}")
    @RoleRequire({ "admin" })
    public String editDeviceForm(@PathVariable int id, Model model) {
        Device device = deviceService.findDeviceById(id);
        model.addAttribute("device", device);
        return "admin/device_management/editDeviceForm";
    }

    @PostMapping("/edit-device")
    public String editDeviceSubmit(@RequestParam("id") int id, @RequestParam("name") String name,
            @RequestParam("description") String description) {
        Device existingDevice = deviceService.findDeviceById(id);
        if (existingDevice != null) {
            existingDevice.setName(name);
            existingDevice.setDescription(description);
            deviceService.saveDevice(existingDevice);
        }
        String success = "Update device successfully";
        return "redirect:/admin/device-management/" + id + "?success=" + success;
    }

    @PostMapping("/delete-device/{id}")
    public String deleteDevice(@PathVariable int id) {
        List<Usage> usageList = usageService.findUsageListYetPaid(id);
        System.out.println(usageList.size());
        if (!usageList.isEmpty()) {
            String err = "The device is on loan or scheduled so cannot be deleted!";
            return "redirect:/admin/device-management?err=" + err;
        }

        deviceService.deleteDeviceById(id);
        String success = "Delete device successfully";
        return "redirect:/admin/device-management?success=" + success;
    }

    @PostMapping("/batch-delete")
    public String batchDeleteDevice(@RequestParam("deviceSelect") int deviceId, @RequestParam("yearInput") int year) {
        String idString = String.valueOf(deviceId) + String.valueOf(year);
        List<Usage> usageList = usageService.findUsageListYetPaidLikeId(Integer.parseInt(idString));
        System.out.println(usageList.size());
        if (!usageList.isEmpty()) {
            String err = "The device is on loan or scheduled so cannot be deleted!";
            return "redirect:/admin/device-management?err=" + err;
        }
        List<Device> devices = deviceService.findAllDevicesLikeId(Integer.parseInt(idString));
        if (devices == null || devices.isEmpty()) {
            String err = "No devices found with the specified ID and year!";
            return "redirect:/admin/device-management?err=" + err;
        }
        for (Device device : devices) {
            deviceService.deleteDeviceById(device.getId());
        }
        String success = "Batch delete devices successfully";
        return "redirect:/admin/device-management?success=" + success;

    }

}
