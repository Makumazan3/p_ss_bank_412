package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.mappers.AtmMapper;
import com.bank.publicinfo.model.Atm;
import com.bank.publicinfo.repositories.AtmRepository;
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

class AtmServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final String ADDRESS = "Russia";
    private static final boolean ALL_HOURS = true;

    @Mock
    private AtmRepository atmRepository;

    @Mock
    private AtmMapper atmMapper;

    @InjectMocks
    private AtmServiceImpl atmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAtmById() {

        Long id = 1L;
        Atm atm = new Atm();
        atm.setId(id);
        AtmDto expectedDto = new AtmDto();
        expectedDto.setId(id);

        when(atmRepository.findById(anyLong())).thenReturn(Optional.of(atm));
        when(atmMapper.toDto(any(Atm.class))).thenReturn(expectedDto);

        AtmDto actualDto = atmService.getAtmById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllAtm() {

        List<Atm> atmList = List.of(new Atm(), new Atm());
        List<AtmDto> expectedDtos = List.of(new AtmDto(), new AtmDto());

        when(atmRepository.findAll()).thenReturn(atmList);
        when(atmMapper.toDtoList(anyList())).thenReturn(expectedDtos);

        List<AtmDto> actualDtoList = atmService.getAllAtm();

        assertEquals(expectedDtos, actualDtoList);
    }

    @Test
    void testSaveAtm() {

        AtmDto atmDto = new AtmDto();
        atmDto.setId(ACCOUNT_ID);
        atmDto.setAddress(ADDRESS);
        atmDto.setAllHours(ALL_HOURS);

        Atm atm = new Atm();
        atm.setId(ACCOUNT_ID);
        atm.setAddress(ADDRESS);
        atm.setAllHours(ALL_HOURS);

        when(atmMapper.toEntity(any(AtmDto.class))).thenReturn(atm);
        when(atmRepository.save(any(Atm.class))).thenReturn(atm);
        when(atmMapper.toDto(any(Atm.class))).thenReturn(atmDto);

        AtmDto savedAtmDto = atmService.addAtm(atmDto);

        assertEquals(atmDto, savedAtmDto);
    }

    @Test
    void testUpdateAtm() {

        AtmDto atmDto = new AtmDto();
        atmDto.setId(ACCOUNT_ID);
        atmDto.setAddress(ADDRESS);
        atmDto.setAllHours(ALL_HOURS);

        Atm atm = new Atm();
        atm.setId(ACCOUNT_ID);
        atm.setAddress(ADDRESS);
        atm.setAllHours(ALL_HOURS);

        when(atmRepository.findById(anyLong())).thenReturn(Optional.of(atm));
        when(atmMapper.toEntity(any(AtmDto.class))).thenReturn(atm);
        when(atmRepository.save(any(Atm.class))).thenReturn(atm);
        when(atmMapper.toDto(any(Atm.class))).thenReturn(atmDto);

        AtmDto updatedAtmDto = atmService.updateAtm(atmDto);

        assertEquals(atmDto, updatedAtmDto);
    }

    @Test
    void testDeleteAtmById() {
        Long id = 1L;

        when(atmRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> atmService.deleteAtm(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        Atm entity = new Atm();
        AtmDto dto = new AtmDto();

        when(atmRepository.findById(id)).thenReturn(Optional.of(entity));
        when(atmMapper.toDto(entity)).thenReturn(dto);

        AtmDto result = atmService.getAtmById(id);

        assertEquals(dto, result);
        verify(atmRepository).findById(id);
        verify(atmMapper).toDto(entity);
    }

    @Test
    void getAllAtmTest() {

        Atm atm = new Atm();
        AtmDto atmDto = new AtmDto();
        List<Atm> atmEntities = List.of(atm);
        List<AtmDto> atmDtos = List.of(atmDto);

        when(atmRepository.findAll()).thenReturn(atmEntities);
        when(atmMapper.toDtoList(atmEntities)).thenReturn(atmDtos);

        List<AtmDto> result = atmService.getAllAtm();

        assertEquals(atmDtos, result);
        verify(atmRepository).findAll();
        verify(atmMapper).toDtoList(atmEntities);
    }
}