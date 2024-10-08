/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project3.Member_Management_SpringBoot.model.Discipline;
import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.model.Usage;
import com.project3.Member_Management_SpringBoot.repository.DisciplineRepository;

import jakarta.transaction.Transactional;

/**
 *
 *
 * @author buing
 */
@Service
public class DisciplineServiceImpl implements DisciplineService {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Override
    public Discipline findStatusByMember(Member member) {
        return disciplineRepository.findByMemberAndStatusAndDescriptionLike(member, 1, "%Khoa the%");
    }

    @Override
    public List<Discipline> findByMember(Member member) {
        return disciplineRepository.findByMember(member);
    }

    @Override
    public Integer findByStatus(Integer status) {
        List<Discipline> disciplines = disciplineRepository.findByStatus(status);
        return disciplines.size();
    }

    @Override
    public Integer findSumFine() {
        return disciplineRepository.findSumFine();
    }

    public List<Discipline> getAllDiscipline() {
        return (List<Discipline>) disciplineRepository.findAll();
    }

    @Override
    public void saveDiscipline(Discipline discipline) {
        disciplineRepository.save(discipline);
    }

    @Override
    public Discipline findById(Integer id) {
        return disciplineRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        disciplineRepository.deleteById(id);
    }

    @Override
    public Boolean checkViolate(Member member, Integer status) {
        List<Discipline> hasViolated = disciplineRepository.findByMemberAndStatus(member, status);
        return !hasViolated.isEmpty();
    }

    // @Override
    // public List<Discipline> findDueTwoMonth(Timestamp deadline) {
    // return disciplineRepository.findDueTwoMonth(deadline);
    // }

    // @Override
    // public List<Discipline> findDueTwoMonth(LocalDateTime deadline) {
    // return disciplineRepository.findDueTwoMonth(deadline);
    // }

    // @Override
    // public List<Discipline> finDueOneMonth(Timestamp deadline) {
    // return disciplineRepository.findDueOneMonth(deadline);
    // }

    // @Override
    // public void updateStatus(int id, int status) {
    // disciplineRepository.updateStatus(id, status);
    // }

    @Override
    @Transactional
    public void updateStatusAfterTwoMonths() {
        Timestamp twoMonthsAgo = new Timestamp(System.currentTimeMillis() - 2L * 30 * 24 * 3600 * 1000);
        disciplineRepository.updateStatusAfterTwoMonths(twoMonthsAgo);
    }

    @Override
    @Transactional
    public void updateStatusAfterOneMonth() {
        Timestamp oneMonthAgoTimestamp = new Timestamp(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000);

        List<Discipline> itemsToProcess = disciplineRepository.findByStatusAndDateBefore(1, oneMonthAgoTimestamp);

        for (Discipline item : itemsToProcess) {
            item.setStatus(0);
            // item.setDate(new Timestamp(System.currentTimeMillis())); // Cập nhật thời
            // gian xử lý mới
            disciplineRepository.save(item);
        }
    }

}
