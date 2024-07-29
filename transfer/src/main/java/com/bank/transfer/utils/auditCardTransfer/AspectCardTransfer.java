package com.bank.transfer.utils.auditCardTransfer;

import com.bank.transfer.dto.transfersDto.CardTransferDto;
import com.bank.transfer.entity.Audit;
import com.bank.transfer.entity.transfers.CardTransfer;
import com.bank.transfer.mappers.CardTransferMapper;
import com.bank.transfer.repositories.AuditRepository;
import com.bank.transfer.exceptions.EntityNotFoundException;
import com.bank.transfer.repositories.CardTransferRepository;
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
 * Класс-аспект для аудита "CardTransfer".
 * Необходим для отслеживания и сохранения
 * информации о процессе создания,
 * редактирования и удаления сущностей.
 **/

@Aspect
@Component
@AllArgsConstructor
public class AspectCardTransfer {

    private final AuditRepository auditRepository;
    private final CardTransferRepository cardTransferRepository;
    private final CardTransferMapper cardTransferMapper;
    private final Logger logger = LoggerFactory.getLogger(AspectCardTransfer.class);

    /**
     * Метод-аспект, который выбирает и сохраняет в БД необходимую
     * аудит-информацию перед выполнением метода,
     * помеченного аннотацией @AuditableCardTransfer.
     *
     * @param joinPoint - это точка соединения, на которой был вызван метод;
     * @param auditable - это аннотация @AuditableCardTransfer, описывающая тип действия;
     **/

    @Before(value = "@annotation(auditable)", argNames = "joinPoint, auditable")
    @Transactional
    public void afterAuditAspect(JoinPoint joinPoint, AuditableCardTransfer auditable) {
        logger.info("старт аспекта");
        if (AspectActionTypeCardTransfer.CREATE_CARD == auditable.auditActionType()) {
            Audit audit = new Audit();
            CardTransfer cardTransfer = cardTransferMapper.toEntity((CardTransferDto) joinPoint.getArgs()[0]);
            addAudit(audit, cardTransfer);
        }
        if (AspectActionTypeCardTransfer.UPDATE_CARD == auditable.auditActionType()) {
            CardTransfer cardTransfer = cardTransferMapper.toEntity((CardTransferDto) joinPoint.getArgs()[0]);
            updateAudit(cardTransfer);
        }
        if (AspectActionTypeCardTransfer.DELETE_CARD == auditable.auditActionType()) {
            Long id = (Long) joinPoint.getArgs()[0];
            CardTransfer cardTransferTransfer = cardTransferRepository.getReferenceById(id);
            deleteAudit(cardTransferTransfer);
        }
    }

    /**
     * Вспомогательный метод, отвечающий за сохранение
     * определённой информации при создании объекта CardTransfer.
     *
     * @param audit - это объект Audit;
     * @param cardTransfer - это объект CardTransfer;
     **/

    private void addAudit(Audit audit, CardTransfer cardTransfer) {
        logger.info("старт аспект-метод addAudit");
        try {
            audit.setEntityJSON(new ObjectMapper().writeValueAsString(cardTransfer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        audit.setId(cardTransfer.getCardNumber());
        audit.setEntityType(CardTransfer.class.getSimpleName());
        audit.setOperationType(AspectActionTypeCardTransfer.CREATE_CARD.name());
        audit.setCreatedBy("Admin");
        audit.setCreatedAt(LocalDateTime.now());
        auditRepository.save(audit);
    }

    /**
     * Вспомогательный метод, отвечающий за сохранение
     * определённой информации при редактировании объекта CardTransfer.
     *
     * @param cardTransfer - это объект CardTransfer;
     **/

    private void updateAudit(CardTransfer cardTransfer) {
        CardTransfer update = cardTransferRepository.findById(cardTransfer.getId())
                .orElseThrow(EntityNotFoundException::new);
        Audit updateAudit = auditRepository.findById(update.getCardNumber())
                .orElseThrow(EntityNotFoundException::new);
        logger.info("старт аспект-метод updateAudit");
        Audit cloneAudit = updateAudit.clone();
        auditRepository.delete(updateAudit);
        cloneAudit.setId(cardTransfer.getCardNumber());
        cloneAudit.setOperationType(AspectActionTypeCardTransfer.UPDATE_CARD.name());
        cloneAudit.setModifiedBy("Admin");
        cloneAudit.setModifiedAt(LocalDateTime.now());
        try {
            cloneAudit.setNewEntityJSON(new ObjectMapper().writeValueAsString(cardTransfer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        auditRepository.save(cloneAudit);
    }

    /**
     * Вспомогательный метод, отвечающий за сохранение
     * определённой информации при удалении объекта CardTransfer.
     *
     * @param cardTransfer - это объект CardTransfer;
     **/

    private void deleteAudit(CardTransfer cardTransfer) {
        Audit deleteAudit = auditRepository.findById(cardTransfer.getCardNumber())
                .orElseThrow(EntityNotFoundException::new);
        logger.info("старт аспект-метод deleteAudit");
        deleteAudit.setOperationType(AspectActionTypeCardTransfer.DELETE_CARD.name());
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