package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.mappers.CertificateMapper;
import com.bank.publicinfo.model.Certificate;
import com.bank.publicinfo.repositories.CertificateRepository;
import com.bank.publicinfo.service.CertificateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    @Override
    @Transactional
    public CertificateDto addCertificate(CertificateDto certificateDto) {
        Certificate newCertificate;
        try {
            newCertificate = certificateRepository.save(certificateMapper.toEntity(certificateDto));
        } catch (Exception e) {
            log.error("Your request is bad!!!! Try again!!!", e.getMessage());
            throw new RuntimeException(e);
        }
        return certificateMapper.toDto(newCertificate);
    }

    @Override
    public List<CertificateDto> getAllCertificate() {
        return certificateMapper.toDtoList(certificateRepository.findAll());
    }

    @Override
    public CertificateDto getCertificateById(Long id) {
        Certificate referenceById = certificateRepository.getReferenceById(id);
        return certificateMapper.toDto(referenceById);
    }

    @Override
    @Transactional
    public void updateCertificate(CertificateDto certificateDto) {
        certificateRepository.save(certificateMapper.toEntity(certificateDto));
    }

    @Override
    @Transactional
    public void deleteCertificate(Long id) {
        certificateRepository.deleteById(id);
    }

}
