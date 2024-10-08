/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;

import com.project3.Member_Management_SpringBoot.model.Device;
import com.project3.Member_Management_SpringBoot.model.Member;
import java.util.List;

import com.project3.Member_Management_SpringBoot.model.Usage;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

/**
 *
 * @author buing
 */
@Service
public interface UsageService {

    List<Usage> getAllUsage();

    Usage getUsageById(Integer id);

    Usage saveUsage(Usage usage);

    void deleteUsageById(Integer id);

    List<Usage> findByMemberIdAndReserveTimeNotNull(Integer memberId);

    List<Usage> findByMemberIdAndBorrowedTimeNotNull(Integer memberId);

    List<Usage> findUsageListYetPaid(Integer deviceId);

    List<Usage> findUsageListYetPaidLikeId(Integer deviceId);

    @Autowired
    void save(Usage usage);

    void deleteAll(Iterable<Usage> usages);

    Boolean reserveDevice(Usage usage);

    List<Usage> findOverdueReservation(Timestamp deadline);

    List<Usage> getBorrowedDevices();

    List<Device> getAvailableDevices();

    Usage findById(Integer ID);

    void returnDevice(Usage usage);

    void borrowDevice(Usage usage);

    List<Usage> statisticsMember(String department, String major, String startDate, String endDate);

    List<Usage> statisticsBorrowedDevice(String name, String startDate, String endDate);

    List<Usage> statisticsBorrowingDevice(String startDate, String endDate);

    Boolean enteringStudyArea(Member member);

    Boolean checkAvailableDevice(Usage usage);

    Boolean checkHasViolated(Usage usage);
}
