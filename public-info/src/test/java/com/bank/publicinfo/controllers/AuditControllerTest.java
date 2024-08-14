package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuditControllerTest {

    @InjectMocks
    private AuditController auditController;

    @Mock
    private AuditService auditService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create() {
        AuditDto auditDto = new AuditDto();

        when(auditService.addAudit(auditDto)).thenReturn(auditDto);

        ResponseEntity<AuditDto> response = auditController.create(auditDto);

        verify(auditService).addAudit(auditDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void showAll() {
        List<AuditDto> auditList = Arrays.asList(new AuditDto(), new AuditDto());

        when(auditService.getAllAudit()).thenReturn(auditList);

        ResponseEntity<List<AuditDto>> response = auditController.showAll();

        verify(auditService).getAllAudit();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getOne() {
        Long id = 1L;
        AuditDto auditDto = new AuditDto();

        when(auditService.getAuditById(id)).thenReturn(auditDto);

        ResponseEntity<AuditDto> response = auditController.getOne(id);

        verify(auditService).getAuditById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(auditDto, response.getBody());
    }

    @Test
    void update() {
        AuditDto auditDto = new AuditDto();

        ResponseEntity<HttpStatus> response = auditController.update(auditDto);

        verify(auditService).updateAudit(auditDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void delete() {
        Long id = 1L;

        ResponseEntity<AuditDto> response = auditController.delete(id);

        verify(auditService).deleteAudit(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
