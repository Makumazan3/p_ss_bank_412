package com.bank.antifraud.aspect;

import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.repository.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Aspect
    @Component
    @RequiredArgsConstructor
    @Slf4j
    public class AuditAspect {

        private final AuditRepository auditRepository;
        private final ObjectMapper objectMapper;

        @AfterReturning(pointcut = "execution(* com.bank.antifraud.service.*.add*(..))", returning = "result")
        public void auditCreateOperation(Object result) {
            log.info("Аудит операции СОЗДАНИЯ начат.");
            auditOperation(result, "CREATE");
        }

        @AfterReturning(pointcut = "execution(* com.bank.antifraud.service.*.update*(..))", returning = "result")
        public void auditUpdateOperation(Object result) {
            log.info("Аудит операции ОБНОВЛЕНИЯ начат.");
            auditOperation(result, "UPDATE");
        }

        @AfterReturning(pointcut = "execution(* com.bank.antifraud.service.*.delete*(..))", returning = "result")
        public void auditDeleteOperation(Object result) {
            log.info("Аудит операции УДАЛЕНИЯ начат.");
            auditOperation(result, "DELETE");
        }

        private void auditOperation(Object result, String operationType) {
            try {
                if (result == null) {
                    log.warn("Аудит операции пропущен: результат равен null для операции {}", operationType);
                    return;
                }

                log.debug("Начало аудита для операции: {}", operationType);

                String entityJson = objectMapper.writeValueAsString(result);
                log.debug("Сериализованная сущность: {}", entityJson);

                String entityType = result.getClass().getSimpleName();
                log.debug("Тип сущности: {}", entityType);

                String createdBy = "system"; // заменить на актуальное значение
                log.debug("Создано: {}", createdBy);

                AuditEntity auditEntity = new AuditEntity();
                auditEntity.setEntityType(entityType);
                auditEntity.setOperationType(operationType);
                auditEntity.setCreatedBy(createdBy);
                auditEntity.setCreatedAt(LocalDateTime.now());
                auditEntity.setEntityJson(entityJson);

                auditRepository.save(auditEntity);
                log.info("Аудит запись создана для операции: {} на сущности: {}", operationType, entityType);
            } catch (Exception e) {
                log.error("Ошибка аудита операции для {}: {}", operationType, e.getMessage(), e);
            }
        }
    }
