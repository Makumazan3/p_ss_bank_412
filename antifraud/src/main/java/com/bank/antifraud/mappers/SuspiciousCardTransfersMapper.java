package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousCardTransfersDto;
import com.bank.antifraud.entity.SuspiciousCardTransfersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper для преобразования между SuspiciousACardTransfersEntity и SuspiciousCardTransfersDto.
 */

@Mapper (componentModel = "spring")
public interface SuspiciousCardTransfersMapper {
/**
 * Преобразует SuspiciousCardTransfersEntity в SuspiciousCardTransfersDto.
 *
 * @param suspiciousCardTransfersEntity сущность подозрительного перевода
 * @return suspiciousCardTransfersDto DTO подозрительного
 */
    SuspiciousCardTransfersDto toDto (SuspiciousCardTransfersEntity suspiciousCardTransfersEntity);

    /**
     * Преобразует SuspiciousCardTransfersDto в SuspiciousCardTransfersEntity
     * @param suspiciousCardTransfersDto DTO
     * @return suspiciousCardTransfersEntity сущность подозрительного перевода
     */
    SuspiciousCardTransfersEntity toEntity (SuspiciousCardTransfersDto suspiciousCardTransfersDto);

    /**
     * Преобразует список SuspiciousCardTransfersEntity в список SuspiciousCardTransfersDto.
     *
     * @param suspiciousCardTransfersEntityList список сущностей подозрительных переводов
     * @return список DTO подозрительных переводов
     */
    List<SuspiciousCardTransfersDto> toDtoList(List<SuspiciousCardTransfersEntity> suspiciousCardTransfersEntityList);


    /**
     * Обновляет существующую сущность SuspiciousCardTransfersEntity на основе данных из SuspiciousCardTransfersDto.
     *
     * @param suspiciousCardTransfersEntity целевая сущность подозрительного перевода
     * @param suspiciousCardTransfersDto DTO подозрительного перевода
     * @return обновленная сущность подозрительного перевода
     */
    @Mapping(target = "id", ignore = true)
    SuspiciousCardTransfersEntity mergeToEntity(@MappingTarget SuspiciousCardTransfersEntity suspiciousCardTransfersEntity, SuspiciousCardTransfersDto suspiciousCardTransfersDto);

}
