/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;

import com.project3.Member_Management_SpringBoot.model.Device;
import com.project3.Member_Management_SpringBoot.model.Discipline;
import com.project3.Member_Management_SpringBoot.model.Member;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

/**
 *
 * @author buing
 */
@Service
public interface MemberService {

    @Autowired
    Member findById(Integer id);

    void saveMember(Member member);

    Boolean checkPasswordUser(Member member, String password);

    Boolean checkUserExists(Integer ID);

    Member findByEmail(String email);

    List<Member> getAllMembers();

    void deleteById(Integer ID);

    void deleteByActiveYear(String activeYear);
    
    Integer statisticsTotalMember(String department, String major, String startDate,String endDate);

    public Page<Member> findAllByNameOrID(String query, Pageable pageable);
}
