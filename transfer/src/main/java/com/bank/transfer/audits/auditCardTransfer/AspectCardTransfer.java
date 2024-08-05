package com.bank.transfer.audits.auditCardTransfer;

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
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
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
     * информацию после выполнением метода,
     * помеченного аннотацией @CreateAuditableAccountTransfer.
     * Отвечает за сохранение определённой информации при создании объекта AccountTransfer.
     * @param joinPoint - это точка соединения, на которой был вызван метод;
     **/

    @After(value = "@annotation(CreateAuditableCardTransfer)", argNames = "joinPoint")
    @Transactional
    public void addAudit(JoinPoint joinPoint) {
        logger.info("старт add аспекта");
        Audit audit = new Audit();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CardTransfer cardTransfer = cardTransferMapper.toEntity((CardTransferDto) joinPoint.getArgs()[0]);
        logger.info("старт аспект-метод addAudit");
        try {
            audit.setEntityJSON(new ObjectMapper().writeValueAsString(cardTransfer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        audit.setId(cardTransfer.getCardNumber());
        audit.setEntityType(CardTransfer.class.getSimpleName());
        audit.setOperationType(methodSignature.getName());
        audit.setCreatedBy("Admin");
        audit.setCreatedAt(LocalDateTime.now());
        auditRepository.save(audit);
    }

    /**
     * Метод-аспект, который выбирает и сохраняет в БД необходимую
     * информацию после выполнением метода,
     * помеченного аннотацией @CreateAuditableAccountTransfer.
     * Отвечает за сохранение определённой информации при редактировании объекта AccountTransfer.
     * @param pjp - это точка соединения, используется для вызова оригинального метода;
     **/

    @Around(value = "@annotation(UpdateAuditableCardTransfer)", argNames = "pjp")
    @Transactional
    public Object addAudit(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Старт before-update аспекта");
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        CardTransfer cardTransfer = cardTransferMapper.toEntity((CardTransferDto) pjp.getArgs()[0]);
        CardTransfer update = cardTransferRepository
                .findById(cardTransfer.getId())
                .orElseThrow(EntityNotFoundException::new);
        Audit updateAudit = auditRepository
                .findById(update.getCardNumber())
                .orElseThrow(EntityNotFoundException::new);
        Object result = pjp.proceed();
        logger.info("Старт вызова оригинального метода");
        Audit cloneAudit = updateAudit.clone();
        logger.info("Старт after-update аспекта");
        auditRepository.delete(updateAudit);
        cloneAudit.setId(cardTransfer.getCardNumber());
        cloneAudit.setOperationType(methodSignature.getName());
        cloneAudit.setModifiedBy("Admin");
        cloneAudit.setModifiedAt(LocalDateTime.now());
        try {
            cloneAudit.setNewEntityJSON(new ObjectMapper().writeValueAsString(cardTransfer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        auditRepository.save(cloneAudit);
        return result;
    }

}