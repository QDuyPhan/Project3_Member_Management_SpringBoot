/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;

import com.project3.Member_Management_SpringBoot.model.Discipline;
import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.model.Usage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author buing
 */
@Service
public interface DisciplineService {

    @Autowired
    Discipline findStatusByMember(Member member);

    @Autowired
    List<Discipline> findByMember(Member member);

    Integer findByStatus(Integer status);

    Integer findSumFine();

    List<Discipline> getAllDiscipline();

    void saveDiscipline(Discipline discipline);

    Discipline findById(Integer id);

    void deleteById(Integer id);

    Boolean checkViolate(Member member, Integer status);

    // List<Discipline> findDueTwoMonth(Timestamp deadline);

    // List<Discipline> findDueTwoMonth(LocalDateTime deadline);

    // List<Discipline> finDueOneMonth(Timestamp deadline);

    // void updateStatus(int id, int status);
    void updateStatusAfterTwoMonths();
}
