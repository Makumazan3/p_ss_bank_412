package com.bank.transfer.utils.auditPhoneTransfer;

import com.bank.transfer.dto.transfersDto.PhoneTransferDto;
import com.bank.transfer.entity.Audit;
import com.bank.transfer.entity.transfers.PhoneTransfer;
import com.bank.transfer.mappers.PhoneTransferMapper;
import com.bank.transfer.repositories.AuditRepository;
import com.bank.transfer.exceptions.EntityNotFoundException;
import com.bank.transfer.repositories.PhoneTransferRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс-аспект для аудита "PhoneTransfer".
 * Необходим для отслеживания и сохранения
 * информации о процессе создания,
 * редактирования и удаления сущностей.
 **/

@Aspect
@Component
@AllArgsConstructor
public class AspectPhoneTransfer {

    private final AuditRepository auditRepository;
    private final PhoneTransferRepository phoneTransferRepository;
    private final PhoneTransferMapper phoneTransferMapper;
    private final Logger logger = LoggerFactory.getLogger(AspectPhoneTransfer.class);

    /**
     * Метод-аспект, который выбирает и сохраняет в БД необходимую
     * аудит-информацию перед выполнением метода,
     * помеченного аннотацией @AuditablePhoneTransfer.
     *
     * @param joinPoint - это точка соединения, на которой был вызван метод;
     * @param auditable - это аннотация @AuditablePhoneTransfer, описывающая тип действия;
     **/

    @Before(value = "@annotation(auditable)", argNames = "joinPoint, auditable")
    @Transactional
    public void afterAuditAspect(JoinPoint joinPoint, AuditablePhoneTransfer auditable) {
        logger.info("старт аспекта");
        if (AspectActionTypePhoneTransfer.CREATE_PHONE == auditable.auditActionType()) {
            Audit audit = new Audit();
            PhoneTransfer phoneTransfer = phoneTransferMapper.toEntity((PhoneTransferDto) joinPoint.getArgs()[0]);
            addAudit(audit, phoneTransfer);
        }
        if (AspectActionTypePhoneTransfer.UPDATE_PHONE == auditable.auditActionType()) {
            PhoneTransfer phoneTransfer = phoneTransferMapper.toEntity((PhoneTransferDto) joinPoint.getArgs()[0]);
            updateAudit(phoneTransfer);
        }
        if (AspectActionTypePhoneTransfer.DELETE_PHONE == auditable.auditActionType()) {
            Long id = (Long) joinPoint.getArgs()[0];
            PhoneTransfer phoneTransferTransfer = phoneTransferRepository.getReferenceById(id);
            deleteAudit(phoneTransferTransfer);
        }
    }

    /**
     * Вспомогательный метод, отвечающий за сохранение
     * определённой информации при создании объекта PhoneTransfer.
     *
     * @param audit - это объект Audit;
     * @param phoneTransfer - это объект PhoneTransfer;
     **/

    private void addAudit(Audit audit, PhoneTransfer phoneTransfer) {
        logger.info("старт аспект-метод addAudit");
        try {
            audit.setEntityJSON(new ObjectMapper().writeValueAsString(phoneTransfer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        audit.setId(phoneTransfer.getPhoneNumber());
        audit.setEntityType(PhoneTransfer.class.getSimpleName());
        audit.setOperationType(AspectActionTypePhoneTransfer.CREATE_PHONE.name());
        audit.setCreatedBy("Admin");
        audit.setCreatedAt(LocalDateTime.now());
        auditRepository.save(audit);
    }

    /**
     * Вспомогательный метод, отвечающий за сохранение
     * определённой информации при редактировании объекта PhoneTransfer.
     *
     * @param phoneTransfer - это объект PhoneTransfer;
     **/

    private void updateAudit(PhoneTransfer phoneTransfer) {
        PhoneTransfer update = phoneTransferRepository.findById(phoneTransfer.getId())
                .orElseThrow(EntityNotFoundException::new);
        Audit updateAudit = auditRepository.findById(update.getPhoneNumber())
                .orElseThrow(EntityNotFoundException::new);
        logger.info("старт аспект-метод updateAudit");
        Audit cloneAudit = updateAudit.clone();
        auditRepository.delete(updateAudit);
        cloneAudit.setId(phoneTransfer.getPhoneNumber());
        cloneAudit.setOperationType(AspectActionTypePhoneTransfer.UPDATE_PHONE.name());
        cloneAudit.setModifiedBy("Admin");
        cloneAudit.setModifiedAt(LocalDateTime.now());
        try {
            cloneAudit.setNewEntityJSON(new ObjectMapper().writeValueAsString(phoneTransfer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        auditRepository.save(cloneAudit);
    }

    /**
     * Вспомогательный метод, отвечающий за сохранение
     * определённой информации при удалении объекта PhoneTransfer.
     *
     * @param phoneTransfer - это объект PhoneTransfer;
     **/

    private void deleteAudit(PhoneTransfer phoneTransfer) {
        Audit deleteAudit = auditRepository.findById(phoneTransfer.getPhoneNumber())
                .orElseThrow(EntityNotFoundException::new);
        logger.info("старт аспект-метод deleteAudit");
        deleteAudit.setOperationType(AspectActionTypePhoneTransfer.DELETE_PHONE.name());
        deleteAudit.setModifiedBy("Admin");
        deleteAudit.setModifiedAt(LocalDateTime.now());
        try {
            deleteAudit.setNewEntityJSON(new ObjectMapper().writeValueAsString(null));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        auditRepository.save(deleteAudit);
    }


}
