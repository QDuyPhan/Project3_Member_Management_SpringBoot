/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;

import com.project3.Member_Management_SpringBoot.model.Usage;
import com.project3.Member_Management_SpringBoot.repository.UsageRepository;

import java.util.List;

import com.project3.Member_Management_SpringBoot.model.Device;
import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.model.Usage;
import com.project3.Member_Management_SpringBoot.repository.UsageRepository;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author buing
 */
@Service
@RequiredArgsConstructor
public class UsageServiceImpl implements UsageService {

    private final UsageRepository usageRepository;

    private final DeviceService deviceService;

    private final MemberService memberService;

    private final DisciplineService disciplineService;

    @Override
    public List<Usage> getAllUsage() {
        return (List<Usage>) usageRepository.findAll();
    }

    @Override
    public Usage getUsageById(Integer id) {
        return usageRepository.findById(id).orElse(null);
    }

    @Override
    public Usage saveUsage(Usage usage) {
        return usageRepository.save(usage);
    }

    @Override
    public void deleteUsageById(Integer id) {
        usageRepository.deleteById(id);
    }

    @Override
    public List<Usage> findByMemberIdAndReserveTimeNotNull(Integer memberId) {
        return usageRepository.findByMemberIdAndReserveTimeNotNull(memberId);
    }

    public void save(Usage usage) {
        usageRepository.save(usage);
    }

    @Override
    public void deleteAll(Iterable<Usage> usages) {
        usageRepository.deleteAll(usages);
    }

    @Override
    public Boolean reserveDevice(Usage usage) {
        Device selectedDevice = usage.getDevice();
        Date reserveDate = new Date(usage.getReserveTime().getTime());
        List<Usage> existedUsage = usageRepository.checkValidateDevice(selectedDevice, reserveDate);
        if (existedUsage.isEmpty())
        {
            save(usage);
            return true;
        }
        return false;
    }

    @Override
    public List<Usage> findOverdueReservation(Timestamp deadline) {
        return usageRepository.findOverdueReservation(deadline);
    }

    @Override
    public List<Usage> getBorrowedDevices() {
        return usageRepository.findByBorrowedTimeNotNullAndPaidTimeIsNull();
    }

    @Override
    public List<Device> getAvailableDevices() {
        return usageRepository.findAvailableDevices();
    }

    @Override
    public Usage findById(Integer ID) {
        return usageRepository.findById(ID).get();
    }

    @Override
    public void returnDevice(Usage usage) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        usage.setPaidTime(currentTimestamp);
        saveUsage(usage);
    }

    @Override
    public void borrowDevice(Usage usage) {
        int memberID = usage.getMember().getId();
        int deviceID = usage.getDevice().getId();
        Member member = memberService.findById(memberID);
        Device device = deviceService.findDeviceById(deviceID);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Usage newBorrow = new Usage();
        newBorrow.setMember(member);
        newBorrow.setDevice(device);
        newBorrow.setBorrowedTime(currentTimestamp);
        saveUsage(newBorrow);
    }

    @Override
    public List<Usage> findByMemberIdAndBorrowedTimeNotNull(Integer memberId) {
        return usageRepository.findByMemberIdAndBorrowedTimeNotNull(memberId);
    }

    @Override
    public List<Usage> findUsageListYetPaid(Integer deviceId) {
        return usageRepository.findUsageListYetPaid(deviceId);
    }

    @Override
    public List<Usage> findUsageListYetPaidLikeId(Integer deviceIdPattern) {
        return usageRepository.findUsageListYetPaidLikeId(deviceIdPattern);
    }

    @Override
    public List<Usage> statisticsMember(String department, String major, String startDate, String endDate) {
        Timestamp startDateTimestamp = null;
        Timestamp endDateTimestamp = null;
        if (startDate != null && !startDate.isBlank() && !startDate.isEmpty())
        {
            LocalDate startDateLocal = LocalDate.parse(startDate);
            startDateTimestamp = Timestamp.valueOf(startDateLocal.atStartOfDay());
        }
        if (endDate != null && !endDate.isBlank() && !endDate.isEmpty())
        {
            LocalDate endDateLocal = LocalDate.parse(endDate);
            endDateTimestamp = Timestamp.valueOf(endDateLocal.atStartOfDay());
        }
        List<Usage> members = usageRepository.statisticsMember(department, major, startDateTimestamp, endDateTimestamp);

        return members;
    }

    @Override
    public List<Usage> statisticsBorrowedDevice(String name, String startDate, String endDate) {
        Timestamp startDateTimestamp = null;
        Timestamp endDateTimestamp = null;
        if (startDate != null && !startDate.isBlank() && !startDate.isEmpty())
        {
            LocalDate startDateLocal = LocalDate.parse(startDate);
            startDateTimestamp = Timestamp.valueOf(startDateLocal.atStartOfDay());
        }
        if (endDate != null && !endDate.isBlank() && !endDate.isEmpty())
        {
            LocalDate endDateLocal = LocalDate.parse(endDate);
            endDateTimestamp = Timestamp.valueOf(endDateLocal.atStartOfDay());
        }
        if (name != null && !name.isBlank() && !name.isEmpty())
        {
            name = "%".concat(name).concat("%");
        } else
        {
            name = null;
        }
        List<Usage> devices = usageRepository.statisticsBorrowedDevice(name, startDateTimestamp, endDateTimestamp);

        return devices;
    }

    @Override
    public List<Usage> statisticsBorrowingDevice(String startDate, String endDate) {
        Timestamp startDateTimestamp = null;
        Timestamp endDateTimestamp = null;
        if (startDate != null && !startDate.isBlank() && !startDate.isEmpty())
        {
            LocalDate startDateLocal = LocalDate.parse(startDate);
            startDateTimestamp = Timestamp.valueOf(startDateLocal.atStartOfDay());
        }
        if (endDate != null && !endDate.isBlank() && !endDate.isEmpty())
        {
            LocalDate endDateLocal = LocalDate.parse(endDate);
            endDateTimestamp = Timestamp.valueOf(endDateLocal.atStartOfDay());
        }
        List<Usage> devices = usageRepository.statisticsBorrowingDevice(startDateTimestamp, endDateTimestamp);

        return devices;
    }

    @Override
    public Boolean enteringStudyArea(Member member) {
        if (disciplineService.findStatusByMember(member) != null)
        {
            return false;
        }
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Usage usage = new Usage();
        usage.setMember(member);
        usage.setEnteredTime(currentTime);
        saveUsage(usage);
        return true;
    }

    @Override
    public Boolean checkAvailableDevice(Usage usage) {
        Integer deviceID = usage.getDevice().getId();
        Device device = deviceService.findDeviceById(deviceID);
        Integer memberID = usage.getMember().getId();
        Date today = new Date();
       List<Usage> reservedOnToday = usageRepository.checkValidateDevice(device, today);
        if (reservedOnToday.isEmpty())
        {
            return true; // hasn't been reserved on today
        }
        Usage reservedToday = reservedOnToday.get(0);
        Integer reMemberID = reservedToday.getMember().getId();
        if (Objects.equals(memberID, reMemberID))
        {
            deleteAll(reservedOnToday);
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkHasViolated(Usage usage) {
          Integer memberID = usage.getMember().getId();
          Member member = memberService.findById(memberID);
          return disciplineService.checkViolate(member, 1);
    }
    
    
}
