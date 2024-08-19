package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.mappers.AuditMapper;
import com.bank.publicinfo.model.Audit;
import com.bank.publicinfo.repositories.AuditRepository;
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

class AuditServiceImplTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final String ENTITY_TYPE = "Russia";
    private static final String OPERATION_TYPE = "Russia";
    private static final String CREATE_BY = "Russia";
    private static final String MODIFIED_BY = "Russia";
    private static final String NEW_ENTITY_JSON = "Russia";
    private static final String ENTITY_JSON = "Russia";

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private AuditMapper auditMapper;

    private AuditDto saveAuditDtoT;

    @InjectMocks
    private AuditServiceImpl auditService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAuditById() {

        Long id = 1L;
        Audit audit = new Audit();
        audit.setId(id);
        AuditDto expectedDto = new AuditDto();
        expectedDto.setId(id);

        when(auditRepository.findById(anyLong())).thenReturn(Optional.of(audit));
        when(auditMapper.toDto(any(Audit.class))).thenReturn(expectedDto);

        AuditDto actualDto = auditService.getAuditById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllAudit() {

        List<Audit> audits = List.of(new Audit(), new Audit());
        List<AuditDto> expectedDtos = List.of(new AuditDto(), new AuditDto());

        when(auditRepository.findAll()).thenReturn(audits);
        when(auditMapper.toDtoList(anyList())).thenReturn(expectedDtos);

        List<AuditDto> actualDtos = auditService.getAllAudit();

        assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void testSaveAudit() {

        AuditDto auditDto = new AuditDto();
        auditDto.setId(ACCOUNT_ID);
        auditDto.setEntityType(ENTITY_TYPE);
        auditDto.setOperationType(OPERATION_TYPE);
        auditDto.setCreatedBy(CREATE_BY);
        auditDto.setModifiedBy(MODIFIED_BY);
        auditDto.setNewEntityJson(NEW_ENTITY_JSON);
        auditDto.setEntityJson(ENTITY_JSON);

        Audit audit = new Audit();
        audit.setId(ACCOUNT_ID);
        audit.setEntityType(ENTITY_TYPE);
        audit.setOperationType(OPERATION_TYPE);
        audit.setCreatedBy(CREATE_BY);
        audit.setModifiedBy(MODIFIED_BY);
        audit.setNewEntityJson(NEW_ENTITY_JSON);
        audit.setEntityJson(ENTITY_JSON);

        when(auditMapper.toEntity(any(AuditDto.class))).thenReturn(audit);
        when(auditRepository.save(any(Audit.class))).thenReturn(audit);
        when(auditMapper.toDto(any(Audit.class))).thenReturn(auditDto);

        AuditDto savedAuditDto = auditService.addAudit(auditDto);

        assertEquals(auditDto, savedAuditDto);
    }

    @Test
    void testUpdateAudit() {

        AuditDto auditDto = new AuditDto();
        auditDto.setId(ACCOUNT_ID);
        auditDto.setEntityType(ENTITY_TYPE);
        auditDto.setOperationType(OPERATION_TYPE);
        auditDto.setCreatedBy(CREATE_BY);
        auditDto.setModifiedBy(MODIFIED_BY);
        auditDto.setNewEntityJson(NEW_ENTITY_JSON);
        auditDto.setEntityJson(ENTITY_JSON);

        Audit audit = new Audit();
        audit.setId(ACCOUNT_ID);
        audit.setEntityType(ENTITY_TYPE);
        audit.setOperationType(OPERATION_TYPE);
        audit.setCreatedBy(CREATE_BY);
        audit.setModifiedBy(MODIFIED_BY);
        audit.setNewEntityJson(NEW_ENTITY_JSON);
        audit.setEntityJson(ENTITY_JSON);

        when(auditRepository.findById(anyLong())).thenReturn(Optional.of(audit));
        when(auditMapper.toEntity(any(AuditDto.class))).thenReturn(audit);
        when(auditRepository.save(any(Audit.class))).thenReturn(audit);
        when(auditMapper.toDto(any(Audit.class))).thenReturn(auditDto);

        AuditDto updatedAuditDto = auditService.updateAudit(auditDto);

        assertEquals(auditDto, updatedAuditDto);
    }

    @Test
    void testDeleteAuditById() {
        Long id = 1L;

        when(auditRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> auditService.deleteAudit(id));
    }

    @Test
    void getByIdTest() {

        Long id = 1L;
        Audit entity = new Audit();
        AuditDto dto = new AuditDto();

        when(auditRepository.findById(id)).thenReturn(Optional.of(entity));
        when(auditMapper.toDto(entity)).thenReturn(dto);

        AuditDto result = auditService.getAuditById(id);

        assertEquals(dto, result);
        verify(auditRepository).findById(id);
        verify(auditMapper).toDto(entity);
    }

    @Test
    void getAllAuditTest() {

        Audit audit = new Audit();
        AuditDto auditDto = new AuditDto();
        List<Audit> auditEntities = List.of(audit);
        List<AuditDto> auditDtos = List.of(auditDto);

        when(auditRepository.findAll()).thenReturn(auditEntities);
        when(auditMapper.toDtoList(auditEntities)).thenReturn(auditDtos);

        List<AuditDto> result = auditService.getAllAudit();

        assertEquals(auditDtos, result);
        verify(auditRepository).findAll();
        verify(auditMapper).toDtoList(auditEntities);
    }
}