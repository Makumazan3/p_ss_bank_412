package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.mappers.LicenseMapper;
import com.bank.publicinfo.model.License;
import com.bank.publicinfo.repositories.LicenseRepository;
import com.bank.publicinfo.service.LicenseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final LicenseMapper licenseMapper;

    @Override
    @Transactional
    public LicenseDto addLicense(LicenseDto licenseDto) {
        License newLicense;
        try {
            newLicense = licenseRepository.save(licenseMapper.toEntity(licenseDto));
        } catch (Exception e) {
            log.error("Your request is bad!!!! Try again!!!", e.getMessage());
            throw new RuntimeException(e);
        }
        return licenseMapper.toDto(newLicense);
    }

    @Override
    public List<LicenseDto> getAllLicense() {
        return licenseMapper.toDtoList(licenseRepository.findAll());
    }

    @Override
    public LicenseDto getLicenseById(Long id) {
        return licenseMapper.toDto(licenseRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public void updateLicense(LicenseDto licenseDto) {
        licenseRepository.save(licenseMapper.toEntity(licenseDto));
    }

    @Override
    @Transactional
    public void deleteLicense(Long id) {
        licenseRepository.deleteById(id);
    }

}
