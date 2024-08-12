package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;

import java.util.List;


public interface SuspiciousPhoneTransfersService {
    List<SuspiciousPhoneTransfersDto> getAllSuspiciousPhoneTransfers();

    SuspiciousPhoneTransfersDto getSuspiciousPhoneTransfersById(Long id);

    SuspiciousPhoneTransfersDto addSuspiciousPhoneTransfers(
            SuspiciousPhoneTransfersDto suspiciousPhoneTransfersDto);

    SuspiciousPhoneTransfersDto updateSuspiciousPhoneTransfers(Long id, SuspiciousPhoneTransfersDto updatedSuspiciousPhoneTransfersDto);

    SuspiciousPhoneTransfersDto deleteSuspiciousPhoneTransfersById(Long id);
}

