package com.project3.Member_Management_SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MemberManagementSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberManagementSpringBootApplication.class, args);
    }

}
