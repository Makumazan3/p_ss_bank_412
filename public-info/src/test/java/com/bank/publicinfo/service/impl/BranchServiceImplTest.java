package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.mappers.BranchMapper;
import com.bank.publicinfo.model.Branch;
import com.bank.publicinfo.repositories.BranchRepository;
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

class BranchServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final String ADDRESS = "Russia";
    private static final int PHONE_NUMBER = 2;
    private static final String CITY = "Russia";

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private BranchMapper branchMapper;

    private BranchDto saveBranchDtoT;

    @InjectMocks
    private BranchServiceImpl branchService;

    @BeforeEach
    void setUp() {MockitoAnnotations.openMocks(this);}

    @Test
    void testGetBranchById() {

        Long id = 1L;
        Branch branch = new Branch();
        branch.setId(id);
        BranchDto expectedDto = new BranchDto();
        expectedDto.setId(id);

        when(branchRepository.findById(anyLong())).thenReturn(Optional.of(branch));
        when(branchMapper.toDto(any(Branch.class))).thenReturn(expectedDto);

        BranchDto actualDto = branchService.getBranchById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllBranch() {

        List<Branch> branchs = List.of(new Branch(), new Branch());
        List<BranchDto> expectedDtos = List.of(new BranchDto(), new BranchDto());

        when(branchRepository.findAll()).thenReturn(branchs);
        when(branchMapper.toDtoList(anyList())).thenReturn(expectedDtos);

        List<BranchDto> actualDtos = branchService.getAllBranch();

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void testSaveBranch() {

        BranchDto branchDto = new BranchDto();
        branchDto.setId(ACCOUNT_ID);
        branchDto.setAddress(ADDRESS);
        branchDto.setPhoneNumber(PHONE_NUMBER);
        branchDto.setCity(CITY);

        Branch branch = new Branch();
        branch.setId(ACCOUNT_ID);
        branch.setAddress(ADDRESS);
        branch.setPhoneNumber(PHONE_NUMBER);
        branch.setCity(CITY);

        when(branchMapper.toEntity(any(BranchDto.class))).thenReturn(branch);
        when(branchRepository.save(any(Branch.class))).thenReturn(branch);
        when(branchMapper.toDto(any(Branch.class))).thenReturn(branchDto);

        BranchDto savedBranchDto = branchService.addBranch(branchDto);

        assertEquals(branchDto, savedBranchDto);
    }

    @Test
    void testUpdateBranch() {

        BranchDto branchDto = new BranchDto();
        branchDto.setId(ACCOUNT_ID);
        branchDto.setAddress(ADDRESS);
        branchDto.setPhoneNumber(PHONE_NUMBER);
        branchDto.setCity(CITY);

        Branch branch = new Branch();
        branch.setId(ACCOUNT_ID);
        branch.setAddress(ADDRESS);
        branch.setPhoneNumber(PHONE_NUMBER);
        branch.setCity(CITY);

        when(branchRepository.findById(anyLong())).thenReturn(Optional.of(branch));
        when(branchMapper.toEntity(any(BranchDto.class))).thenReturn(branch);
        when(branchRepository.save(any(Branch.class))).thenReturn(branch);
        when(branchMapper.toDto(any(Branch.class))).thenReturn(branchDto);

        BranchDto updatedBranchDto = branchService.updateBranch(branchDto);

        assertEquals(branchDto, updatedBranchDto);
    }

    @Test
    void testDeleteBranchById() {
        Long id = 1L;

        when(branchRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> branchService.deleteBranch(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        Branch entity = new Branch();
        BranchDto dto = new BranchDto();

        when(branchRepository.findById(id)).thenReturn(Optional.of(entity));
        when(branchMapper.toDto(entity)).thenReturn(dto);

        BranchDto result = branchService.getBranchById(id);

        assertEquals(dto, result);
        verify(branchRepository).findById(id);
        verify(branchMapper).toDto(entity);
    }

    @Test
    void getAllBranchTest() {

        Branch branch = new Branch();
        BranchDto branchDto = new BranchDto();
        List<Branch> branchEntities = List.of(branch);
        List<BranchDto> branchDtoList = List.of(branchDto);

        when(branchRepository.findAll()).thenReturn(branchEntities);
        when(branchMapper.toDtoList(branchEntities)).thenReturn(branchDtoList);

        List<BranchDto> result = branchService.getAllBranch();

        assertEquals(branchDtoList, result);
        verify(branchRepository).findAll();
        verify(branchMapper).toDtoList(branchEntities);
    }
}