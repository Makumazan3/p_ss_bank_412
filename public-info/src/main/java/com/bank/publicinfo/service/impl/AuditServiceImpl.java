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
        if (auditDto.getEntityType() == null ||
                auditDto.getOperationType() == null ||
                auditDto.getCreatedBy() == null ||
                auditDto.getEntityJson() == null) {
            throw new NullPointerException("This field can't be null!!");
        }

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
        Audit audit = auditRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Audit not found with id: " + id));
        return auditMapper.toDto(audit);
    }

    @Override
    @Transactional
    public AuditDto updateAudit(AuditDto auditDto) {
        if (auditDto.getEntityType() == null ||
                auditDto.getOperationType() == null ||
                auditDto.getCreatedBy() == null ||
                auditDto.getEntityJson() == null) {
            throw new NullPointerException("This field can't be null!!");
        }

        Audit audit = auditRepository.save(auditMapper.toEntity(auditDto));
        return auditMapper.toDto(audit);
    }

    @Override
    @Transactional
    public void deleteAudit(Long id) {
        if (!auditRepository.existsById(id)) {
            throw new NullPointerException("Audit not found with id: " + id);
        }

        auditRepository.deleteById(id);
    }
}