/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.repository;

import com.project3.Member_Management_SpringBoot.model.Device;
import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.model.Usage;

import java.util.List;

import java.sql.Timestamp;
import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author buing
 */
@Repository
public interface UsageRepository extends CrudRepository<Usage, Integer> {

    Usage findMemberById(Integer id);

    @Query("SELECT u FROM Usage u WHERE u.member.id = :memberId AND u.reserveTime IS NOT NULL AND u.paidTime IS NULL")
    List<Usage> findByMemberIdAndReserveTimeNotNull(@Param("memberId") Integer memberId);
    @Query("SELECT u FROM Usage u WHERE u.member.id = :memberId AND u.borrowedTime IS NOT NULL AND u.paidTime IS NULL")
    List<Usage> findByMemberIdAndBorrowedTimeNotNull(@Param("memberId") Integer memberId);

    @Query("SELECT u FROM Usage u WHERE u.device = ?1 AND DATE(u.reserveTime) = ?2")
    List<Usage> checkValidateDevice(Device selectedDevice, Date reserveDate);

    @Query(value = "SELECT * FROM `thongtinsd` u WHERE DATE_ADD(u.tgdatcho, INTERVAL 1 HOUR) <=  :deadline", nativeQuery = true)
    List<Usage> findOverdueReservation(Timestamp deadline);

    List<Usage> findByBorrowedTimeNotNullAndPaidTimeIsNull();

    @Query("SELECT d FROM Device d LEFT JOIN Usage u ON d.id = u.device.id AND u.paidTime IS NULL AND u.borrowedTime IS NOT NULL WHERE u.device IS NULL")
    List<Device> findAvailableDevices();

    @Query("SELECT u FROM Usage u WHERE u.member.id = :memberId AND u.borrowedTime IS NOT NULL AND u.paidTime IS NOT NULL")
    List<Usage> findByMemberIdAndBorrowedTimeIsNotNull(@Param("memberId") Integer memberId);

//    @Query("SELECT m FROM Member m JOIN FETCH m.usages WHERE m.id = :memberId")
//    Member findMemberWithUsages(@Param("memberId") Integer memberId);
    @Query("SELECT u FROM Usage u where u.device.id = :deviceId AND u.paidTime IS NULL")
    List<Usage> findUsageListYetPaid(@Param("deviceId") Integer deviceId);

    @Query("SELECT u FROM Usage u WHERE CAST(u.device.id AS string) LIKE CONCAT(:deviceIdPattern, '%') AND u.paidTime IS NULL")
    List<Usage> findUsageListYetPaidLikeId(@Param("deviceIdPattern") int deviceId);

    @Query("SELECT u FROM Usage u JOIN u.member m WHERE (:department IS NULL OR m.department = :department) AND (:major IS NULL OR m.major = :major) AND u.enteredTime IS NOT NULL AND (:startDate IS NULL OR u.enteredTime >= :startDate) AND (:endDate IS NULL OR u.enteredTime <= :endDate)")
    List<Usage> statisticsMember(@Param("department") String department, @Param("major") String major, @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT u FROM Usage u JOIN u.device d WHERE u.paidTime IS NOT NULL AND (:startDate IS NULL OR u.borrowedTime >= :startDate) AND (:endDate IS NULL OR u.paidTime <= :endDate) AND (:name IS NULL OR d.name LIKE :name)")
    List<Usage> statisticsBorrowedDevice(@Param("name") String name, @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT u FROM Usage u JOIN u.device d WHERE u.borrowedTime IS NOT NULL AND u.paidTime IS NULL AND (:startDate IS NULL OR u.borrowedTime >= :startDate) AND (:endDate IS NULL OR u.borrowedTime <= :endDate)")
    List<Usage> statisticsBorrowingDevice(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
