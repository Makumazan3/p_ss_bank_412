package com.bank.antifraud.service;


import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;

import java.util.List;

public interface SuspiciousAccountTransfersService {

    List<SuspiciousAccountTransfersDto> getAllSuspiciousAccountTransfers();

    SuspiciousAccountTransfersDto getSuspiciousAccountTransfersById(Long id);

    SuspiciousAccountTransfersDto addSuspiciousAccountTransfers(
            SuspiciousAccountTransfersDto suspiciousAccountTransfersDto);

    SuspiciousAccountTransfersDto updateSuspiciousAccountTransfers(Long id, SuspiciousAccountTransfersDto updatedSuspiciousAccountTransfersDto);

    SuspiciousAccountTransfersDto deleteSuspiciousAccountTransfersById(Long id);

}