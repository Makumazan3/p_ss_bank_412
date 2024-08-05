package com.bank.transfer.services.auditService;

import com.bank.transfer.dto.AuditDto;
import java.util.List;

/**
 * Интерфейс-сервис для работы с Audit.
 * Предоставляет методы для получения
 * записей Audit.
 **/

public interface AuditService {

    /**
     * Метод, предоставляющий информацию
     * о всех записях Audit из БД.
     *
     * @return возвращает информацию
     * о всех записях Audit из БД в формате DTO.
     **/

    List<AuditDto> getAllAudit();

    /**
     * Метод, предоставляющий информацию
     * об определённой записи Audit из БД по id.
     *
     * @return возвращает информацию
     * о записи Audit из БД по id в формате DTO.
     **/

    AuditDto getAuditById(Long auditId);
}
