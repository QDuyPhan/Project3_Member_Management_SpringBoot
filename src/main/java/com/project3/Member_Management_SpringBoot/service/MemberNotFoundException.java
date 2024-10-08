package com.project3.Member_Management_SpringBoot.service;

public class MemberNotFoundException extends Exception {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
