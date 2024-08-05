package com.bank.transfer.mappers;

import com.bank.transfer.dto.transfersDto.AccountTransferDto;
import com.bank.transfer.entity.transfers.AccountTransfer;
import org.mapstruct.Mapper;
import java.util.List;

/**
 * Интерфейс-маппер для преобразования объектов между DTO и Entity AccountTransfer.
 **/

@Mapper (componentModel = "spring")
public interface AccountTransferMapper  {
    AccountTransfer toEntity(AccountTransferDto accountTransferDto);
    AccountTransferDto toDto(AccountTransfer accountTransfer);
    List<AccountTransferDto> toDtoList(List<AccountTransfer> accountTransfers);
}
