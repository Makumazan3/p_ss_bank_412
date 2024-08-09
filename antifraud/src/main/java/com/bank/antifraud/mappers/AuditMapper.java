package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper (componentModel = "spring")
public interface AuditMapper {
    AuditDto toDto(AuditEntity entity);

    AuditEntity toEntity(AuditDto auditDto);

    List<AuditDto> toDtoList(List<AuditEntity> auditEntityList);
}
