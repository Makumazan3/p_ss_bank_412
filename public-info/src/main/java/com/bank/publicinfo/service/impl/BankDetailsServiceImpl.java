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
        if (bankDetailsDto.getCity() == null ||
                bankDetailsDto.getJointStockCompany() == null ||
                bankDetailsDto.getName() == null) {
            throw new NullPointerException("This field can't be null!!");
        }

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
        BankDetails bankDetails = bankDetailsRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("BankDetails not found with id: " + id));
        return bankDetailsMapper.toDto(bankDetails);
    }

    @Override
    @Transactional
    public BankDetailsDto updateBankDetails(BankDetailsDto bankDetailsDto) {
        if (bankDetailsDto.getCity() == null ||
                bankDetailsDto.getJointStockCompany() == null ||
                bankDetailsDto.getName() == null) {
            throw new NullPointerException("This field can't be null!!");
        }

        BankDetails bankDetails = bankDetailsRepository.save(bankDetailsMapper.toEntity(bankDetailsDto));
        return bankDetailsMapper.toDto(bankDetails);
    }

    @Override
    @Transactional
    public void deleteBankDetails(Long id) {
        if (!bankDetailsRepository.existsById(id)) {
            throw new NullPointerException("BankDetails not found with id: " + id);
        }

        bankDetailsRepository.deleteById(id);
    }
}