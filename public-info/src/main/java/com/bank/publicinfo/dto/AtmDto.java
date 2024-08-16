package com.bank.publicinfo.dto;

import lombok.Data;

import java.time.OffsetTime;

@Data
public class AtmDto {

    private Long id;
    private String address;
    private OffsetTime startOfWork;
    private OffsetTime endOfWork;
    private boolean allHours;
    private Long branchId;
}