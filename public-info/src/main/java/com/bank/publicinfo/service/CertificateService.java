package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.model.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateService {

    CertificateDto addCertificate(CertificateDto certificateDto);

    List<CertificateDto> getAllCertificate();

    CertificateDto getCertificateById(Long id);

    void updateCertificate(CertificateDto certificateDto);

    void deleteCertificate(Long id);

}
