package com.bank.publicinfo.aspects;

import com.bank.publicinfo.model.Audit;
import com.bank.publicinfo.repositories.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;

    @AfterReturning(pointcut = "execution(* com.bank.publicinfo.service.*.add*(..))"
            , returning = "finishResult")
    public void afterCreateMethod(Object finishResult) {
        log.info("The new audit is started to create.");
        auditOperation(finishResult, "CREATE");
    }

    @AfterReturning(pointcut = "execution(* com.bank.publicinfo.service.*.update*(..))"
            , returning = "finishResult")
    public void afterUpdateMethod(Object finishResult) {
        log.info("The audit is started to update.");
        auditOperation(finishResult, "UPDATE");
    }

    @AfterReturning(pointcut = "execution(* com.bank.publicinfo.service.*.delete*(..))"
            , returning = "finishResult")
    public void afterDeleteMethod(Object finishResult) {
        log.info("This audit is started to delete.");
        auditOperation(finishResult, "DELETE");
    }

    private void auditOperation(Object finishResult, String operationType) {
        try {
            if (finishResult == null) {
                log.warn("Finish result will be null for operation {}", operationType);
                return;
            }
            log.debug("Start audit for operation: {}", operationType);

            String entityJson = objectMapper.writeValueAsString(finishResult);
            log.debug("Serializable entity: {}", entityJson);

            String entityType = finishResult.getClass().getSimpleName();
            log.debug("Entity type: {}", entityType);

            String createdBy = "system";
            log.debug("Created by: {}", createdBy);

            Audit auditEntity = new Audit();
            auditEntity.setEntityType(entityType);
            auditEntity.setOperationType(operationType);
            auditEntity.setCreatedBy(createdBy);
            auditEntity.setCreatedAt(LocalDateTime.now());
            auditEntity.setEntityJson(entityJson);

            auditRepository.save(auditEntity);
            log.info("The operation {} by entity: {} was successful!", operationType, entityType);

        } catch (Exception e) {
            log.error("Error audit operation for {}: {}", operationType, e.getMessage(), e);
        }
    }

}
