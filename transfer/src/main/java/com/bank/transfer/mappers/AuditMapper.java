package com.bank.transfer.mappers;

import com.bank.transfer.dto.AuditDto;
import com.bank.transfer.entity.Audit;
import org.mapstruct.Mapper;
import java.util.List;

/**
 * Интерфейс-маппер для преобразования объектов между DTO и Entity Audit.
 **/

@Mapper (componentModel = "spring")
public interface AuditMapper {
    AuditDto toDto (Audit audit);
    List<AuditDto> toDtoList (List<Audit> auditList);
}
