package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.mappers.BankDetailsMapper;
import com.bank.publicinfo.model.BankDetails;
import com.bank.publicinfo.repositories.BankDetailsRepository;
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

class BankDetailsServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final Long BIK = 2L;
    private static final Long INN = 3L;
    private static final Long KPP = 4L;
    private static final Long COR_ACCOUNT = 5L;
    private static final String CITY = "Russia";
    private static final String JOINT_STOCK_COMPANY = "Russia";
    private static final String NAME = "Russia";

    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @Mock
    private BankDetailsMapper bankDetailsMapper;

    private BankDetailsDto saveBankDetailsDtoT;

    @InjectMocks
    private BankDetailsServiceImpl bankDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBankDetailsById() {

        Long id = 1L;
        BankDetails bankDetails = new BankDetails();
        bankDetails.setId(id);
        BankDetailsDto expectedDto = new BankDetailsDto();
        expectedDto.setId(id);

        when(bankDetailsRepository.findById(anyLong())).thenReturn(Optional.of(bankDetails));
        when(bankDetailsMapper.toDto(any(BankDetails.class))).thenReturn(expectedDto);

        BankDetailsDto actualDto = bankDetailsService.getBankDetailsById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllBankDetails() {

        List<BankDetails> bankDetailss = List.of(new BankDetails(), new BankDetails());
        List<BankDetailsDto> expectedDtos = List.of(new BankDetailsDto(), new BankDetailsDto());

        when(bankDetailsRepository.findAll()).thenReturn(bankDetailss);
        when(bankDetailsMapper.toDtoList(anyList())).thenReturn(expectedDtos);

        List<BankDetailsDto> actualDtos = bankDetailsService.getAllBankDetails();

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void testSaveBankDetails() {

        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(ACCOUNT_ID);
        bankDetailsDto.setBik(BIK);
        bankDetailsDto.setInn(INN);
        bankDetailsDto.setKpp(KPP);
        bankDetailsDto.setCorAccount(COR_ACCOUNT);
        bankDetailsDto.setCity(CITY);
        bankDetailsDto.setJointStockCompany(JOINT_STOCK_COMPANY);
        bankDetailsDto.setName(NAME);

        BankDetails bankDetails = new BankDetails();
        bankDetails.setId(ACCOUNT_ID);
        bankDetails.setBik(BIK);
        bankDetails.setInn(INN);
        bankDetails.setKpp(KPP);
        bankDetails.setCorAccount(COR_ACCOUNT);
        bankDetails.setCity(CITY);
        bankDetails.setJointStockCompany(JOINT_STOCK_COMPANY);
        bankDetails.setName(NAME);

        when(bankDetailsMapper.toEntity(any(BankDetailsDto.class))).thenReturn(bankDetails);
        when(bankDetailsRepository.save(any(BankDetails.class))).thenReturn(bankDetails);
        when(bankDetailsMapper.toDto(any(BankDetails.class))).thenReturn(bankDetailsDto);

        BankDetailsDto savedBankDetailsDto = bankDetailsService.addBankDetails(bankDetailsDto);

        assertEquals(bankDetailsDto, savedBankDetailsDto);
    }

    @Test
    void testUpdateBankDetails() {

        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(ACCOUNT_ID);
        bankDetailsDto.setBik(BIK);
        bankDetailsDto.setInn(INN);
        bankDetailsDto.setKpp(KPP);
        bankDetailsDto.setCorAccount(COR_ACCOUNT);
        bankDetailsDto.setCity(CITY);
        bankDetailsDto.setJointStockCompany(JOINT_STOCK_COMPANY);
        bankDetailsDto.setName(NAME);

        BankDetails bankDetails = new BankDetails();
        bankDetails.setId(ACCOUNT_ID);
        bankDetails.setBik(BIK);
        bankDetails.setInn(INN);
        bankDetails.setKpp(KPP);
        bankDetails.setCorAccount(COR_ACCOUNT);
        bankDetails.setCity(CITY);
        bankDetails.setJointStockCompany(JOINT_STOCK_COMPANY);
        bankDetails.setName(NAME);

        when(bankDetailsRepository.findById(anyLong())).thenReturn(Optional.of(bankDetails));
        when(bankDetailsMapper.toEntity(any(BankDetailsDto.class))).thenReturn(bankDetails);
        when(bankDetailsRepository.save(any(BankDetails.class))).thenReturn(bankDetails);
        when(bankDetailsMapper.toDto(any(BankDetails.class))).thenReturn(bankDetailsDto);

        BankDetailsDto updatedBankDetailsDto = bankDetailsService.updateBankDetails(bankDetailsDto);

        assertEquals(bankDetailsDto, updatedBankDetailsDto);
    }

    @Test
    void testDeleteBankDetailsById() {
        Long id = 1L;

        when(bankDetailsRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> bankDetailsService.deleteBankDetails(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        BankDetails entity = new BankDetails();
        BankDetailsDto dto = new BankDetailsDto();

        when(bankDetailsRepository.findById(id)).thenReturn(Optional.of(entity));
        when(bankDetailsMapper.toDto(entity)).thenReturn(dto);

        BankDetailsDto result = bankDetailsService.getBankDetailsById(id);

        assertEquals(dto, result);
        verify(bankDetailsRepository).findById(id);
        verify(bankDetailsMapper).toDto(entity);
    }

    @Test
    void getAllBankDetailsTest() {

        BankDetails bankDetails = new BankDetails();
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        List<BankDetails> bankDetailsEntities = List.of(bankDetails);
        List<BankDetailsDto> bankDetailsDtos = List.of(bankDetailsDto);

        when(bankDetailsRepository.findAll()).thenReturn(bankDetailsEntities);
        when(bankDetailsMapper.toDtoList(bankDetailsEntities)).thenReturn(bankDetailsDtos);

        List<BankDetailsDto> result = bankDetailsService.getAllBankDetails();

        assertEquals(bankDetailsDtos, result);
        verify(bankDetailsRepository).findAll();
        verify(bankDetailsMapper).toDtoList(bankDetailsEntities);
    }
}