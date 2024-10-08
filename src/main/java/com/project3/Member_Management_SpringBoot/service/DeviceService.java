/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;

import com.project3.Member_Management_SpringBoot.model.Device;
import java.sql.Timestamp;
import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeviceService {
    @Autowired
    List<Device> findAllDevices();
    
    public Page<Device> findAllByNameOrID(String query, Pageable pageable);
    List<Device> findAllDevicesLikeId(int deviceId);

    List<Device> getAvailableDevices();

    List<Device> searchDeviceByName(String name);

    Device saveDevice(Device device);

    Device findDeviceById(int id);

    void deleteDeviceById(int id);
    
    Integer statisticsTotalBorrowedDevice(String name, String startDate, String endDate);
    
    Integer statisticsTotalBorrowingDevice(String startDate, String endDate);
}
