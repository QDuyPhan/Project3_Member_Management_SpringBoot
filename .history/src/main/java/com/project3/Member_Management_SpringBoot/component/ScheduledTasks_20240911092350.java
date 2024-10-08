/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.component;

import com.project3.Member_Management_SpringBoot.model.Usage;
import com.project3.Member_Management_SpringBoot.service.UsageService;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author buing
 */
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final UsageService usageService;
    
    @Scheduled(fixedRate = 60000) // 1 minute = 60000 milliseconds
    public void deleteReservationAutomatically() {
        List<Usage> overdueReservation = findOverdueReservation();
        if (!overdueReservation.isEmpty())
        {
            Iterable<Usage> usages = overdueReservation;
            usageService.deleteAll(usages);
            log.info("Delete overdue reservation successfully");
        } else {
            log.info("Not have any overdue reservation");
        }
    }
    

    public List<Usage> findOverdueReservation() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        
        
//        String formattedTimestamp = dateFormat.format(currentTime);
        
        
        return usageService.findOverdueReservation(currentTime);
    }
}
