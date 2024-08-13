package com.bank.publicinfo.mappers;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.model.License;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LicenseMapper {

    LicenseDto toDto(License license);

    License toEntity(LicenseDto licenseDto);

    List<LicenseDto> toDtoList(List<License> lisenses);

}
