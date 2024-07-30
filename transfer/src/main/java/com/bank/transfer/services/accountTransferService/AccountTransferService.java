package com.bank.transfer.services.accountTransferService;

import com.bank.transfer.dto.transfersDto.AccountTransferDto;
import java.util.List;

/**
 * Интерфейс-сервис для работы с AccountTransfer.
 * Предоставляет методы для создания, получения, обновления
 * и удаления записей AccountTransfer.
 **/

public interface AccountTransferService {

    /**
     * Метод, предоставляющий информацию
     * о всех записях AccountTransfer из БД.
     *
     * @return возвращает информацию
     * о всех записях AccountTransfer из БД в формате DTO.
     **/

    List<AccountTransferDto> getAllAccountTransfer();

    /**
     * Метод, предоставляющий информацию
     * об определённой записи AccountTransfer из БД по id.
     *
     * @return возвращает информацию
     * о записи AccountTransfer из БД по id в формате DTO.
     **/

    AccountTransferDto getAccountTransferById(Long accountTransferId);

    /**
     * Метод, создающий новую запись
     * о AccountTransfer в БД.
     *
     * @param accountTransferDto - это объект DTO.
     **/

    void createAccountTransfer(AccountTransferDto accountTransferDto);

    /**
     * Метод, редактирующий существующую запись
     * о AccountTransfer в БД.
     *
     * @param accountTransferDto - это объект DTO.
     **/

    void updateAccountTransfer(AccountTransferDto accountTransferDto);

    /**
     * Метод, удаляющий запись
     * о AccountTransfer в БД.
     *
     * @param accountTransferId - это поиск AccountTransfer по id.
     **/

    void deleteAccountTransferById(long accountTransferId);

}
