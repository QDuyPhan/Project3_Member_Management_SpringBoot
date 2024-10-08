/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.repository;

import com.project3.Member_Management_SpringBoot.model.Discipline;
import com.project3.Member_Management_SpringBoot.model.Member;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author buing
 */
@Repository
public interface DisciplineRepository extends CrudRepository<Discipline, Integer> {
    Discipline findByMemberAndStatusAndDescriptionLike(Member member, Integer status, String description);

    List<Discipline> findByMember(Member member);

    List<Discipline> findByStatus(Integer status);

    @Query("SELECT SUM(d.fine) FROM Discipline d WHERE d.status = 0")
    Integer findSumFine();

    List<Discipline> findByMemberAndStatus(Member member, Integer status);

    // @Query(value = "SELECT * FROM xyly u WHERE DATE_ADD(u.ngayxl, INTERVAL 2
    // MONTH) <= :deadline", nativeQuery = true)
    // List<Discipline> findDueTwoMonth(Timestamp deadline);

    // @Query(value = "SELECT xl FROM xyly xl WHERE xl.ngayxl <= :deadline",
    // nativeQuery = true)
    // List<Discipline> findDueTwoMonth(LocalDateTime deadline);

    // @Query(value = "SELECT * FROM xyly u WHERE DATE_ADD(u.ngayxl, INTERVAL 1
    // MONTH) <= :deadline", nativeQuery = true)
    // List<Discipline> findDueOneMonth(Timestamp deadline);

    // @Query("UPDATE xuly xl set xl.TrangthaiXL = :status where xl.id = :MaXL")
    // void updateStatus(@Param("MaXL") int id, @Param("TrangthaiXL") int status);

    @Modifying
    @Query("UPDATE Discipline d SET d.status = 0 WHERE d.date <= :twoMonthsAgo AND d.status = 1")
    void updateStatusAfterTwoMonths(@Param("twoMonthsAgo") Timestamp twoMonthsAgo);
}
