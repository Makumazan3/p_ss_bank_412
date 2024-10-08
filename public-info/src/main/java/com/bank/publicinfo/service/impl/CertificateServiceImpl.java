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
        if (certificateDto.getPhoto() == null) {
            throw new NullPointerException("This field can't be null!!");
        }

        final Certificate newCertificate;
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
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found with id: " + id));
        return certificateMapper.toDto(certificate);
    }

    @Override
    @Transactional
    public CertificateDto updateCertificate(CertificateDto certificateDto) {
        if (certificateDto.getPhoto() == null) {
            throw new NullPointerException("This field can't be null!!");
        }

        Certificate certificate = certificateRepository.save(certificateMapper.toEntity(certificateDto));
        return certificateMapper.toDto(certificate);
    }

    @Override
    @Transactional
    public void deleteCertificate(Long id) {
        if (!certificateRepository.existsById(id)) {
            throw new RuntimeException("Certificate not found with id: " + id);
        }
        certificateRepository.deleteById(id);
    }
}