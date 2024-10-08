/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;

import com.project3.Member_Management_SpringBoot.model.Device;
import com.project3.Member_Management_SpringBoot.repository.DeviceRepository;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Device> findAllDevices() {
        return (List<Device>) deviceRepository.findAll();
    }
    
    @Override
    public List<Device> getAvailableDevices() {
        return deviceRepository.findAvailableDevices();
    }

    @Override
    public List<Device> searchDeviceByName(String name) {
        return deviceRepository.findByNameLike("%" + name + "%");
    }

    @Override
    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device findDeviceById(int id) {
        return deviceRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteDeviceById(int id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public List<Device> findAllDevicesLikeId(int deviceId) {
        return deviceRepository.findAllDevicesLikeId(deviceId);
    }

    @Override
    public Integer statisticsTotalBorrowedDevice(String name, String startDateStr, String endDateStr) {
       Timestamp startDateTimestamp = null;
        Timestamp endDateTimestamp = null;
        if (startDateStr != null && !startDateStr.isBlank() && !startDateStr.isEmpty())
        {
            LocalDate startDateLocal = LocalDate.parse(startDateStr);
            startDateTimestamp = Timestamp.valueOf(startDateLocal.atStartOfDay());
        }
        if (endDateStr != null && !endDateStr.isBlank() && !endDateStr.isEmpty())
        {
            LocalDate endDateLocal = LocalDate.parse(endDateStr);
            endDateTimestamp = Timestamp.valueOf(endDateLocal.atStartOfDay());
        }
        if (name != null && !name.isBlank() && !name.isEmpty())
        {
            name = "%".concat(name).concat("%");
        } else {
            name = null;
        }
        return deviceRepository.statisticsTotalBorrowedDevice(name, startDateTimestamp, endDateTimestamp);
    }

    @Override
    public Integer statisticsTotalBorrowingDevice(String startDateStr, String endDateStr) {
        Timestamp startDateTimestamp = null;
        Timestamp endDateTimestamp = null;
        if (startDateStr != null && !startDateStr.isBlank() && !startDateStr.isEmpty())
        {
            LocalDate startDateLocal = LocalDate.parse(startDateStr);
            startDateTimestamp = Timestamp.valueOf(startDateLocal.atStartOfDay());
        }
        if (endDateStr != null && !endDateStr.isBlank() && !endDateStr.isEmpty())
        {
            LocalDate endDateLocal = LocalDate.parse(endDateStr);
            endDateTimestamp = Timestamp.valueOf(endDateLocal.atStartOfDay());
        }

        return deviceRepository.statisticsTotalBorrowingDevice(startDateTimestamp, endDateTimestamp);
    }

    @Override
    public Page<Device> findAllByNameOrID(String query, Pageable pageable) {
        return deviceRepository.findAllByNameOrID(query, pageable);
    }
}
