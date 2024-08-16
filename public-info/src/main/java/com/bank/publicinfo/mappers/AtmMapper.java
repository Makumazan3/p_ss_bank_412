package com.bank.publicinfo.mappers;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.model.Atm;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AtmMapper {

    AtmDto toDto(Atm atm);

    Atm toEntity(AtmDto atmDto);

    List<AtmDto> toDtoList(List<Atm> atms);
}