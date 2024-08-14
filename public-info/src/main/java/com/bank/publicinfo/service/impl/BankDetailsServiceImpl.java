package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.mappers.BankDetailsMapper;
import com.bank.publicinfo.model.BankDetails;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.service.BankDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class BankDetailsServiceImpl implements BankDetailsService {

    private final BankDetailsRepository bankDetailsRepository;
    private final BankDetailsMapper bankDetailsMapper;


    @Override
    @Transactional
    public BankDetailsDto addBankDetails(BankDetailsDto bankDetailsDto) {
        final BankDetails newBankDetails;
        try {
            newBankDetails = bankDetailsRepository.save(bankDetailsMapper.toEntity(bankDetailsDto));
        } catch (Exception e) {
            log.error("Your request is bad!!!! Try again!!!", e.getMessage());
            throw new RuntimeException(e);
        }
        return bankDetailsMapper.toDto(newBankDetails);
    }

    @Override
    public List<BankDetailsDto> getAllBankDetails() {
        return bankDetailsMapper.toDtoList(bankDetailsRepository.findAll());
    }

    @Override
    public BankDetailsDto getBankDetailsById(Long id) {
        return bankDetailsMapper.toDto(bankDetailsRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public void updateBankDetails(BankDetailsDto bankDetailsDto) {
        bankDetailsRepository.save(bankDetailsMapper.toEntity(bankDetailsDto));
    }

    @Override
    @Transactional
    public void deleteBankDetails(Long id) {
        bankDetailsRepository.deleteById(id);
    }

}
