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
        log.info("Получение всех аудитов");
        return auditMapper.toDtoList(auditRepository.findAll());
    }

    @Override
    public AuditDto getById(Long id) {
        log.info("Получение аудита по ID: {}", id);
        return auditMapper.toDto(auditRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Аудит не найден с ID  " + id)));
    }

    @Override
    public AuditDto createAudit(AuditDto auditDto) {
        AuditEntity auditEntity = auditMapper.toEntity(auditDto);
        auditEntity.setCreatedAt(LocalDateTime.now());
        log.info("Создание аудита с данными: {}", auditEntity);

        if (auditEntity.getCreatedBy() == null) {
            throw new IllegalArgumentException("Поле CreatedBy обязательно для заполнения");
        }
        if (auditEntity.getEntityType() == null) {
            throw new IllegalArgumentException("Поле EntityType обязательно для заполнения");
        }
        if (auditEntity.getOperationType() == null) {
            throw new IllegalArgumentException("Поле OperationType обязательно для заполнения");
        }
        if (auditEntity.getEntityJson() == null) {
            throw new IllegalArgumentException("Поле EntityJson обязательно для заполнения");
        }

        AuditEntity savedAuditEntity = auditRepository.save(auditEntity);
        log.info("Аудит успешно создан с ID: {}", savedAuditEntity.getId());
        return auditMapper.toDto(savedAuditEntity);
    }

    @Override
    public AuditDto updateAudit(Long id, AuditDto auditDto) {
        log.info("Обновление аудита с ID: {}", id);
        return auditRepository.findById(id)
                .map(existingAudit -> {
                    AuditEntity updatedEntity = auditMapper.toEntity(auditDto);
                    updatedEntity.setId(existingAudit.getId());
                    return auditMapper.toDto(auditRepository.save(updatedEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException("Аудит не найден с ID " + id));
    }

    @Override
    public void deleteAudit(Long id) {
        log.info("Удаление аудита с ID: {}", id);
        if (!auditRepository.existsById(id)) {
            throw new EntityNotFoundException("Аудит не найден с ID " + id);
        }
        auditRepository.deleteById(id);
        log.info("Аудит успешно удален с ID: {}", id);
    }
}
