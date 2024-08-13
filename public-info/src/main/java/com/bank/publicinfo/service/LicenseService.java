package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.LicenseDto;

import java.util.List;

public interface LicenseService {

    LicenseDto addLicense(LicenseDto licenseDto);

    List<LicenseDto> getAllLicense();

    LicenseDto getLicenseById(Long id);

    void updateLicense(LicenseDto licenseDto);

    void deleteLicense(Long id);

}
