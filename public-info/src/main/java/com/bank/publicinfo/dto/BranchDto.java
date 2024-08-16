package com.bank.publicinfo.dto;

import lombok.Data;

import java.time.OffsetTime;

@Data
public class BranchDto {

    private Long id;
    private String address;
    private int phoneNumber;
    private String city;
    private OffsetTime startOfWork;
    private OffsetTime endOfWork;
}