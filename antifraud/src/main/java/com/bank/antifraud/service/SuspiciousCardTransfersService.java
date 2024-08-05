
package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousCardTransfersDto;

import java.util.List;


public interface SuspiciousCardTransfersService {
    List<SuspiciousCardTransfersDto> getAllSuspiciousCardTransfers();

    SuspiciousCardTransfersDto getSuspiciousCardTransfersById(Long id);

    SuspiciousCardTransfersDto addSuspiciousCardTransfers(
            SuspiciousCardTransfersDto suspiciousCardTransfersDto);

    SuspiciousCardTransfersDto updateSuspiciousCardTransfers(Long id, SuspiciousCardTransfersDto updatedSuspiciousCardTransfersDto);

    SuspiciousCardTransfersDto deleteSuspiciousCardTransfersById(Long id);
}