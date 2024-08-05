package com.bank.antifraud.service.Imp;


import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.mappers.AuditMapper;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class AuditServiceImp implements AuditService {
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    @Override
    public List<AuditDto> getAll() {
        return auditMapper.toDtoList(auditRepository.findAll());
    }

    @Override
    public AuditDto getById(Long id) {
        return auditMapper.toDto(auditRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Audit not found with id " + id)));
    }

    @Override
    public AuditDto createAudit(AuditDto auditDto) {
        // Преобразование DTO в сущность
        AuditEntity auditEntity = auditMapper.toEntity(auditDto);

        // Установка текущей даты и времени для поля createdAt
        auditEntity.setCreatedAt(LocalDateTime.now());

        // Логирование значений полей перед проверкой
        log.info("Creating audit with data: {}", auditEntity);

        // Проверка и установка обязательных полей
        if (auditEntity.getCreatedBy() == null) {
            throw new IllegalArgumentException("CreatedBy field is required");
        }
        if (auditEntity.getEntityType() == null) {
            throw new IllegalArgumentException("EntityType field is required");
        }
        if (auditEntity.getOperationType() == null) {
            throw new IllegalArgumentException("OperationType field is required");
        }
        if (auditEntity.getEntityJson() == null) {
            throw new IllegalArgumentException("EntityJson field is required");
        }

        // Сохранение сущности в базе данных
        AuditEntity savedAuditEntity = auditRepository.save(auditEntity);

        // Преобразование сохраненной сущности обратно в DTO
        return auditMapper.toDto(savedAuditEntity);
    }



    @Override
    public AuditDto updateAudit(Long id, AuditDto auditDto) {
        return auditRepository.findById(id)
                .map(existingAudit -> {
                    AuditEntity updatedEntity = auditMapper.toEntity(auditDto);
                    updatedEntity.setId(existingAudit.getId());
                    return auditMapper.toDto(auditRepository.save(updatedEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException("Audit not found with id " + id));
    }

    @Override
    public void deleteAudit(Long id) {
        if (!auditRepository.existsById(id)) {
            throw new EntityNotFoundException("Audit not found with id " + id);
        }
        auditRepository.deleteById(id);
    }

}
