package com.bank.transfer.utils.auditAccountTransfer;

import com.bank.transfer.dto.transfersDto.AccountTransferDto;
import com.bank.transfer.entity.Audit;
import com.bank.transfer.entity.transfers.AccountTransfer;
import com.bank.transfer.mappers.AccountTransferMapper;
import com.bank.transfer.repositories.AccountTransferRepository;
import com.bank.transfer.repositories.AuditRepository;
import com.bank.transfer.exceptions.EntityNotFoundException;
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
 * Класс-аспект для аудита "AccountTransfer".
 * Необходим для отслеживания и сохранения
 * информации о процессе создания,
 * редактирования и удаления объектов.
 **/

@Aspect
@Component
@AllArgsConstructor
public class AspectAccountTransfer {

    private final AuditRepository auditRepository;
    private final AccountTransferRepository accountTransferRepository;
    private final AccountTransferMapper accountTransferMapper;
    private final Logger logger = LoggerFactory.getLogger(AspectAccountTransfer.class);

    /**
     * Метод-аспект, который выбирает и сохраняет в БД необходимую
     * информацию перед выполнением метода,
     * помеченного аннотацией @AuditableAccountTransfer.
     *
     * @param joinPoint - это точка соединения, на которой был вызван метод;
     * @param auditable - это аннотация @AuditableAccountTransfer, описывающая тип действия;
     **/

    @Before(value = "@annotation(auditable)", argNames = "joinPoint, auditable")
    @Transactional
    public void afterAuditAspect(JoinPoint joinPoint, AuditableAccountTransfer auditable) {
        logger.info("старт аспекта");
        if (AspectActionTypeAccountTransfer.CREATE_ACCOUNT == auditable.auditActionType()) {
            Audit audit = new Audit();
            AccountTransfer accountTransfer = accountTransferMapper.toEntity((AccountTransferDto) joinPoint.getArgs()[0]);
            addAudit(audit, accountTransfer);
        }
        if (AspectActionTypeAccountTransfer.UPDATE_ACCOUNT == auditable.auditActionType()) {
            AccountTransfer accountTransfer = accountTransferMapper.toEntity((AccountTransferDto) joinPoint.getArgs()[0]);
            updateAudit(accountTransfer);
        }
        if (AspectActionTypeAccountTransfer.DELETE_ACCOUNT == auditable.auditActionType()) {
            Long id = (Long) joinPoint.getArgs()[0];
            AccountTransfer accountTransfer = accountTransferRepository.getReferenceById(id);
            deleteAudit(accountTransfer);
        }
    }

    /**
     * Вспомогательный метод, отвечающий за сохранение
     * определённой информации при создании объекта AccountTransfer.
     *
     * @param audit - это объект Audit;
     * @param accountTransfer - это объект AccountTransfer;
     **/

    private void addAudit(Audit audit, AccountTransfer accountTransfer) {
        logger.info("старт аспект-метод addAudit");
        try {
            audit.setEntityJSON(new ObjectMapper().writeValueAsString(accountTransfer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        audit.setId(accountTransfer.getAccountNumber());
        audit.setEntityType(AccountTransfer.class.getSimpleName());
        audit.setOperationType(AspectActionTypeAccountTransfer.CREATE_ACCOUNT.name());
        audit.setCreatedBy("Admin");
        audit.setCreatedAt(LocalDateTime.now());
        auditRepository.save(audit);
    }

    /**
     * Вспомогательный метод, отвечающий за сохранение
     * определённой информации при редактировании объекта AccountTransfer.
     *
     * @param accountTransfer - это объект AccountTransfer;
     **/

    private void updateAudit(AccountTransfer accountTransfer) {
        AccountTransfer update = accountTransferRepository.findById(accountTransfer.getId())
                .orElseThrow(EntityNotFoundException::new);
        Audit updateAudit = auditRepository
                .findById(update.getAccountNumber())
                .orElseThrow(EntityNotFoundException::new);
        logger.info("старт аспект-метод updateAudit");
        Audit cloneAudit = updateAudit.clone();
        auditRepository.delete(updateAudit);
        cloneAudit.setId(accountTransfer.getAccountNumber());
        cloneAudit.setOperationType(AspectActionTypeAccountTransfer.UPDATE_ACCOUNT.name());
        cloneAudit.setModifiedBy("Admin");
        cloneAudit.setModifiedAt(LocalDateTime.now());
        try {
            cloneAudit.setNewEntityJSON(new ObjectMapper().writeValueAsString(accountTransfer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        auditRepository.save(cloneAudit);
    }

    /**
     * Вспомогательный метод, отвечающий за сохранение
     * определённой информации при удалении объекта AccountTransfer.
     *
     * @param accountTransfer - это объект AccountTransfer;
     **/

    private void deleteAudit(AccountTransfer accountTransfer) {
        Audit deleteAudit = auditRepository.findById(accountTransfer.getAccountNumber())
                .orElseThrow(EntityNotFoundException::new);
        logger.info("старт аспект-метод deleteAudit");
        deleteAudit.setOperationType(AspectActionTypeAccountTransfer.DELETE_ACCOUNT.name());
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
