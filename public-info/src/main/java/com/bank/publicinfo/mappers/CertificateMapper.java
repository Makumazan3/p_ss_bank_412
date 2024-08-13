package com.bank.publicinfo.mappers;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.model.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CertificateMapper {

    CertificateDto toDto(Certificate certificate);

    Certificate toEntity(CertificateDto certificateDto);

    List<CertificateDto> toDtoList(List<Certificate> certificates);

}
