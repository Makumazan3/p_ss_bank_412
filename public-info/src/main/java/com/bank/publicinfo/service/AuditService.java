package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.AuditDto;

import java.util.List;

public interface AuditService {

    AuditDto addAudit(AuditDto auditDto);

    List<AuditDto> getAllAudit();

    AuditDto getAuditById(Long id);

    void updateAudit(AuditDto auditDto);

    void deleteAudit(Long id);
}