package com.bank.transfer.services.auditService;

import com.bank.transfer.dto.AuditDto;
import com.bank.transfer.mappers.AuditMapper;
import com.bank.transfer.repositories.AuditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Класс, имплементирующий сервис AuditService.
 * Обеспечивает функциональность получения
 * записей Audit.
 **/

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    private final AuditMapper auditMapper;
    private final Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);

    @Override
    public List<AuditDto> getAllAudit() {
        logger.info("Старт сервис-метода getAllAudit");
        return auditMapper.toDtoList(auditRepository.findAll());
    }

    @Override
    public AuditDto getAuditById(Long auditId) {
        logger.info("Старт сервис-метода getAuditById");
        return auditMapper.toDto(auditRepository.getReferenceById(auditId));
    }

}
