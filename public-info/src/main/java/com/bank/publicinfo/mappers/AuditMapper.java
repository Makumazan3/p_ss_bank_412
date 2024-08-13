package com.bank.publicinfo.mappers;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.model.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuditMapper {


    AuditDto toDto(Audit audit);

    Audit toEntity(AuditDto auditDto);

    List<AuditDto> toDtoList(List<Audit> audits);

}
