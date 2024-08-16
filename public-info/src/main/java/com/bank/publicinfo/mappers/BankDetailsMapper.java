package com.bank.publicinfo.mappers;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.model.BankDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankDetailsMapper {

    BankDetailsDto toDto(BankDetails bankDetails);

    BankDetails toEntity(BankDetailsDto bankDetailsDto);

    List<BankDetailsDto> toDtoList(List<BankDetails> bankDetailsList);
}