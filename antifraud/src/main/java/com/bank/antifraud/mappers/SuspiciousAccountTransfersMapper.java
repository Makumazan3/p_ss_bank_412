package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.entity.SuspiciousAccountTransfersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

/**
 * Mapper для преобразования между SuspiciousAccountTransfersEntity и SuspiciousAccountTransfersDto.
 */

@Mapper (componentModel = "spring")
public interface SuspiciousAccountTransfersMapper {
/**
 * Преобразует SuspiciousAccountTransfersEntity в SuspiciousAccountTransfersDto.
 *
 * @param suspiciousAccountTransfersEntity сущность подозрительного перевода
 * @return suspiciousAccountTransfersDto DTO подозрительного
 */

    SuspiciousAccountTransfersDto toDto (SuspiciousAccountTransfersEntity suspiciousAccountTransfersEntity);

    /**
     * Преобразует SuspiciousAccountTransfersDto в SuspiciousAccountTransfersEntity
     * @param suspiciousAccountTransfersDto DTO
     * @return suspiciousAccountTransfersEntity сущность подозрительного перевода
     */

    SuspiciousAccountTransfersEntity toEntity (SuspiciousAccountTransfersDto suspiciousAccountTransfersDto);
    /**
     * Преобразует список SuspiciousAccountTransfersEntity в список SuspiciousAccountTransfersDto.
     *
     * @param suspiciousAccountTransfersEntityList список сущностей подозрительных переводов
     * @return список DTO подозрительных переводов
     */
    List<SuspiciousAccountTransfersDto> toDtoList(List<SuspiciousAccountTransfersEntity> suspiciousAccountTransfersEntityList);


    /**
     * Обновляет существующую сущность SuspiciousAccountTransfersEntity на основе данных из SuspiciousAccountTransfersDto.
     *
     * @param suspiciousAccountTransfersEntity целевая сущность подозрительного перевода
     * @param suspiciousAccountTransfersDto DTO подозрительного перевода
     * @return обновленная сущность подозрительного перевода
     */
    @Mapping(target = "id", ignore = true)
    SuspiciousAccountTransfersEntity mergeToEntity(@MappingTarget SuspiciousAccountTransfersEntity suspiciousAccountTransfersEntity, SuspiciousAccountTransfersDto suspiciousAccountTransfersDto);
}

