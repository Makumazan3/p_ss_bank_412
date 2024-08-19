package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.mappers.LicenseMapper;
import com.bank.publicinfo.model.License;
import com.bank.publicinfo.repositories.LicenseRepository;
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

class LicenseServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final byte[] PHOTO = {1};

    @Mock
    private LicenseRepository licenseRepository;

    @Mock
    private LicenseMapper licenseMapper;

    private LicenseDto saveLicenseDtoT;

    @InjectMocks
    private LicenseServiceImpl licenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLicenseById() {

        Long id = 1L;
        License license = new License();
        license.setId(id);
        LicenseDto expectedDto = new LicenseDto();
        expectedDto.setId(id);

        when(licenseRepository.findById(anyLong())).thenReturn(Optional.of(license));
        when(licenseMapper.toDto(any(License.class))).thenReturn(expectedDto);

        LicenseDto actualDto = licenseService.getLicenseById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllLicense() {

        List<License> licenses = List.of(new License(), new License());
        List<LicenseDto> expectedDtos = List.of(new LicenseDto(), new LicenseDto());

        when(licenseRepository.findAll()).thenReturn(licenses);
        when(licenseMapper.toDtoList(anyList())).thenReturn(expectedDtos);

        List<LicenseDto> actualDtos = licenseService.getAllLicense();

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void testSaveLicense() {

        LicenseDto licenseDto = new LicenseDto();
        licenseDto.setId(ACCOUNT_ID);
        licenseDto.setPhoto(PHOTO);

        License license = new License();
        license.setId(ACCOUNT_ID);
        licenseDto.setPhoto(PHOTO);

        when(licenseMapper.toEntity(any(LicenseDto.class))).thenReturn(license);
        when(licenseRepository.save(any(License.class))).thenReturn(license);
        when(licenseMapper.toDto(any(License.class))).thenReturn(licenseDto);

        LicenseDto savedLicenseDto = licenseService.addLicense(licenseDto);

        assertEquals(licenseDto, savedLicenseDto);
    }

    @Test
    void testDeleteLicenseById() {
        Long id = 1L;

        when(licenseRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> licenseService.deleteLicense(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        License entity = new License();
        LicenseDto dto = new LicenseDto();

        when(licenseRepository.findById(id)).thenReturn(Optional.of(entity));
        when(licenseMapper.toDto(entity)).thenReturn(dto);

        LicenseDto result = licenseService.getLicenseById(id);

        assertEquals(dto, result);
        verify(licenseRepository).findById(id);
        verify(licenseMapper).toDto(entity);
    }

    @Test
    void getAllLicenseTest() {

        License license = new License();
        LicenseDto licenseDto = new LicenseDto();
        List<License> licenseEntities = List.of(license);
        List<LicenseDto> licenseDtos = List.of(licenseDto);

        when(licenseRepository.findAll()).thenReturn(licenseEntities);
        when(licenseMapper.toDtoList(licenseEntities)).thenReturn(licenseDtos);

        List<LicenseDto> result = licenseService.getAllLicense();

        assertEquals(licenseDtos, result);
        verify(licenseRepository).findAll();
        verify(licenseMapper).toDtoList(licenseEntities);
    }
}