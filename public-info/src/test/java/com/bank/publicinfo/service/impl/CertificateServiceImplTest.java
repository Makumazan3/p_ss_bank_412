package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.mappers.CertificateMapper;
import com.bank.publicinfo.model.Certificate;
import com.bank.publicinfo.repositories.CertificateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CertificateServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final byte[] PHOTO = {1};

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    private CertificateDto saveCertificateDtoT;

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @BeforeEach
    void setUp() {MockitoAnnotations.openMocks(this);}

    @Test
    void testGetCertificateById() {

        Long id = 1L;
        Certificate certificate = new Certificate();
        certificate.setId(id);
        CertificateDto expectedDto = new CertificateDto();
        expectedDto.setId(id);

        when(certificateRepository.findById(anyLong())).thenReturn(Optional.of(certificate));
        when(certificateMapper.toDto(any(Certificate.class))).thenReturn(expectedDto);

        CertificateDto actualDto = certificateService.getCertificateById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllCertificate() {

        List<Certificate> certificates = List.of(new Certificate(), new Certificate());
        List<CertificateDto> expectedDtos = List.of(new CertificateDto(), new CertificateDto());

        when(certificateRepository.findAll()).thenReturn(certificates);
        when(certificateMapper.toDtoList(anyList())).thenReturn(expectedDtos);

        List<CertificateDto> actualDtos = certificateService.getAllCertificate();

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void testSaveCertificate() {

        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(ACCOUNT_ID);
        certificateDto.setPhoto(PHOTO);

        Certificate certificate = new Certificate();
        certificate.setId(ACCOUNT_ID);
        certificate.setPhoto(PHOTO);

        when(certificateMapper.toEntity(any(CertificateDto.class))).thenReturn(certificate);
        when(certificateRepository.save(any(Certificate.class))).thenReturn(certificate);
        when(certificateMapper.toDto(any(Certificate.class))).thenReturn(certificateDto);

        CertificateDto savedCertificateDto = certificateService.addCertificate(certificateDto);

        assertEquals(certificateDto, savedCertificateDto);
    }

    @Test
    void testDeleteCertificateById() {
        Long id = 1L;

        when(certificateRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> certificateService.deleteCertificate(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        Certificate entity = new Certificate();
        CertificateDto dto = new CertificateDto();

        when(certificateRepository.findById(id)).thenReturn(Optional.of(entity));
        when(certificateMapper.toDto(entity)).thenReturn(dto);

        CertificateDto result = certificateService.getCertificateById(id);

        assertEquals(dto, result);
        verify(certificateRepository).findById(id);
        verify(certificateMapper).toDto(entity);
    }

    @Test
    void getAllCertificateTest() {

        Certificate certificate = new Certificate();
        CertificateDto certificateDto = new CertificateDto();
        List<Certificate> certificateEntities = List.of(certificate);
        List<CertificateDto> certificateDtos = List.of(certificateDto);

        when(certificateRepository.findAll()).thenReturn(certificateEntities);
        when(certificateMapper.toDtoList(certificateEntities)).thenReturn(certificateDtos);

        List<CertificateDto> result = certificateService.getAllCertificate();

        assertEquals(certificateDtos, result);
        verify(certificateRepository).findAll();
        verify(certificateMapper).toDtoList(certificateEntities);
    }
}