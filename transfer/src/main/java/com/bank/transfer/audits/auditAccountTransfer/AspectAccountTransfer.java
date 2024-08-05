package com.bank.transfer.audits.auditAccountTransfer;

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
     * информацию после выполнением метода,
     * помеченного аннотацией @CreateAuditableAccountTransfer.
     * Отвечает за сохранение определённой информации при создании объекта AccountTransfer.
     * @param joinPoint - это точка соединения, на которой был вызван метод;
     **/

    @After(value = "@annotation(CreateAuditableAccountTransfer)", argNames = "joinPoint")
    @Transactional
    public void addAudit(JoinPoint joinPoint) {
        logger.info("старт add аспекта");
        Audit audit = new Audit();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AccountTransfer accountTransfer = accountTransferMapper.toEntity((AccountTransferDto) joinPoint.getArgs()[0]);
        logger.info("старт аспект-метод addAudit");
        try {
            audit.setEntityJSON(new ObjectMapper().writeValueAsString(accountTransfer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        audit.setId(accountTransfer.getAccountNumber());
        audit.setEntityType(AccountTransfer.class.getSimpleName());
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

    @Around(value = "@annotation(UpdateAuditableAccountTransfer)", argNames = "pjp")
    @Transactional
    public Object addAudit(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Старт before-update аспекта");
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        AccountTransfer accountTransfer = accountTransferMapper.toEntity((AccountTransferDto) pjp.getArgs()[0]);
        AccountTransfer update = accountTransferRepository
                .findById(accountTransfer.getId())
                .orElseThrow(EntityNotFoundException::new);
        Audit updateAudit = auditRepository
                .findById(update.getAccountNumber())
                .orElseThrow(EntityNotFoundException::new);
        Object result = pjp.proceed();
        logger.info("Старт вызова оригинального метода");
        Audit cloneAudit = updateAudit.clone();
        logger.info("Старт after-update аспекта");
        auditRepository.delete(updateAudit);
        cloneAudit.setId(accountTransfer.getAccountNumber());
        cloneAudit.setOperationType(methodSignature.getName());
        cloneAudit.setModifiedBy("Admin");
        cloneAudit.setModifiedAt(LocalDateTime.now());
        try {
            cloneAudit.setNewEntityJSON(new ObjectMapper().writeValueAsString(accountTransfer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        auditRepository.save(cloneAudit);
        return result;
    }

}
