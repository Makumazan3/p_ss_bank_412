package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransfersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper для преобразования между SuspiciousACardTransfersEntity и SuspiciousCardTransfersDto.
 */

@Mapper (componentModel = "spring")
public interface SuspiciousPhoneTransfersMapper {
/**
 * Преобразует SuspiciousPhoneTransfersEntity в SuspiciousPhoneTransfersDto.
 *
 * @param suspiciousPhoneTransfersEntity сущность подозрительного перевода
 * @return suspiciousPhoneTransfersDto DTO подозрительного
 */

    SuspiciousPhoneTransfersDto toDto (SuspiciousPhoneTransfersEntity suspiciousPhoneTransfersEntity);

    /**
     * Преобразует SuspiciousPhoneTransfersDto в SuspiciousPhoneTransfersEntity
     * @param suspiciousPhoneTransfersDto DTO
     * @return suspiciousPhoneTransfersEntity сущность подозрительного перевода
     */
    SuspiciousPhoneTransfersEntity toEntity (SuspiciousPhoneTransfersDto suspiciousPhoneTransfersDto);

    /**
     * Преобразует список SuspiciousPhoneTransfersEntity в список SuspiciousPhoneTransfersDto.
     *
     * @param suspiciousPhoneTransfersEntityList список сущностей подозрительных переводов
     * @return список DTO подозрительных переводов
     */
    List<SuspiciousPhoneTransfersDto> toDtoList(List<SuspiciousPhoneTransfersEntity> suspiciousPhoneTransfersEntityList);


    /**
     * Обновляет существующую сущность SuspiciousPhonedTransfersEntity на основе данных из SuspiciousPhoneTransfersDto.
     *
     * @param suspiciousPhoneTransfersEntity целевая сущность подозрительного перевода
     * @param suspiciousPhoneTransfersDto DTO подозрительного перевода
     * @return обновленная сущность подозрительного перевода
     */
    @Mapping(target = "id", ignore = true)
    SuspiciousPhoneTransfersEntity mergeToEntity(@MappingTarget SuspiciousPhoneTransfersEntity suspiciousPhoneTransfersEntity, SuspiciousPhoneTransfersDto suspiciousPhoneTransfersDto);





}
