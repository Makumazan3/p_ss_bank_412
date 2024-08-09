package com.bank.antifraud.service;

import com.bank.antifraud.dto.AuditDto;
import java.util.List;

public interface AuditService {
    List<AuditDto> getAll();

    AuditDto getById(Long id);

    AuditDto createAudit(AuditDto auditDto);

    AuditDto updateAudit(Long id, AuditDto auditDto);

    void deleteAudit(Long id);
}

