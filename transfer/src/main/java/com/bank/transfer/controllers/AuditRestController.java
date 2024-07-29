package com.bank.transfer.controllers;

import com.bank.transfer.dto.AuditDto;
import com.bank.transfer.services.audit.AuditService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Класс-контроллер для предоставления информации
 * связанной с Audit.
 * Обрабатывает HTTP-запросы, связанные с операциями
 * получения записей об Audit.
 **/

@RestController
@AllArgsConstructor
public class AuditRestController {

    private final AuditService auditService;
    private final Logger logger = LoggerFactory.getLogger(AuditRestController.class);

    /**
     * Метод, предоставляющий информацию
     * о всех записях Audit из БД.
     *
     * @return возвращает информацию
     * о всех записях Audit из БД в формате DTO.
     **/

    @GetMapping("/audit/getAll")
    public List<AuditDto> getAllAudit() {
        logger.info("Старт контроллер-метода getAllAudit");
        return auditService.getAllAudit();
    }

    /**
     * Метод, предоставляющий информацию
     * об определённой записи Audit из БД по id.
     *
     * @return возвращает информацию
     * о записи Audit из БД по id в формате DTO.
     **/

    @GetMapping("/audit/get/{id}")
    public AuditDto getAuditById(@PathVariable long id) {
        logger.info("Старт контроллер-метода getAuditById");
        return auditService.getAuditById(id);
    }
}
