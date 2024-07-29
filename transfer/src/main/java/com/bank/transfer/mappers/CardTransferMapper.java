package com.bank.transfer.mappers;

import com.bank.transfer.dto.transfersDto.CardTransferDto;
import com.bank.transfer.entity.transfers.CardTransfer;
import org.mapstruct.Mapper;
import java.util.List;

/**
 * Интерфейс-маппер для преобразования объектов между DTO и Entity CardTransfer.
 **/

@Mapper(componentModel = "spring")
public interface CardTransferMapper {

    CardTransfer toEntity(CardTransferDto cardTransferDto);

    CardTransferDto toDto(CardTransfer cardTransfer);

    List<CardTransferDto> toDtoList(List<CardTransfer> cardTransferList);
}
