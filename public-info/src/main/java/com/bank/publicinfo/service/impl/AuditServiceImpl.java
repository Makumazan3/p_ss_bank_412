package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.mappers.AuditMapper;
import com.bank.publicinfo.model.Audit;
import com.bank.publicinfo.repositories.AuditRepository;
import com.bank.publicinfo.service.AuditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    @Override
    @Transactional
    public AuditDto addAudit(AuditDto auditDto) {
        final Audit newAudit;
        try {
            newAudit = auditRepository.save(auditMapper.toEntity(auditDto));
        } catch (Exception e) {
            log.error("Your request is bad!!!! Try again!!!", e.getMessage());
            throw new RuntimeException(e);
        }
        return auditMapper.toDto(newAudit);
    }

    @Override
    public List<AuditDto> getAllAudit() {
        return auditMapper.toDtoList(auditRepository.findAll());
    }

    @Override
    public AuditDto getAuditById(Long id) {
        return auditMapper.toDto(auditRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public void updateAudit(AuditDto auditDto) {
        auditRepository.save(auditMapper.toEntity(auditDto));
    }

    @Override
    @Transactional
    public void deleteAudit(Long id) {
        auditRepository.deleteById(id);
    }

}
