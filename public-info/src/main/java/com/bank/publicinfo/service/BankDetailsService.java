package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BankDetailsDto;

import java.util.List;

public interface BankDetailsService {

    BankDetailsDto addBankDetails(BankDetailsDto bankDetailsDto);

    List<BankDetailsDto> getAllBankDetails();

    BankDetailsDto getBankDetailsById(Long id);

    void updateBankDetails(BankDetailsDto bankDetailsDto);

    void deleteBankDetails(Long id);
}