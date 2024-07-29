package com.bank.transfer.mappers;

import com.bank.transfer.dto.transfersDto.PhoneTransferDto;
import com.bank.transfer.entity.transfers.PhoneTransfer;
import org.mapstruct.Mapper;
import java.util.List;

/**
 * Интерфейс-маппер для преобразования объектов между DTO и Entity PhoneTransfer.
 **/

@Mapper(componentModel = "spring")
public interface PhoneTransferMapper {
    PhoneTransfer toEntity (PhoneTransferDto phoneTransferDto);
    PhoneTransferDto toDto (PhoneTransfer phoneTransfer);
    List<PhoneTransferDto> toDtoList (List<PhoneTransfer> phoneTransferList);
}
