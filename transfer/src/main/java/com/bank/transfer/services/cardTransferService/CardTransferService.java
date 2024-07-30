package com.bank.transfer.services.cardTransferService;

import com.bank.transfer.dto.transfersDto.CardTransferDto;
import java.util.List;

/**
 * Интерфейс-сервис для работы с CardTransfer.
 * Предоставляет методы для создания, получения, обновления
 * и удаления записей CardTransfer.
 **/

public interface CardTransferService {

    /**
     * Метод, предоставляющий информацию
     * о всех записях CardTransfer из БД.
     *
     * @return возвращает информацию
     * о всех записях CardTransfer из БД в формате DTO.
     **/

    List<CardTransferDto> getAllCardTransfers();

    /**
     * Метод, предоставляющий информацию
     * об определённой записи CardTransfer из БД по id.
     *
     * @return возвращает информацию
     * о записи CardTransfer из БД по id в формате DTO.
     **/

    CardTransferDto getCardTransferById(Long cardTransferId);

    /**
     * Метод, создающий новую запись
     * о CardTransfer в БД.
     *
     * @param cardTransferDto - это объект DTO.
     **/

    void createCardTransfer(CardTransferDto cardTransferDto);

    /**
     * Метод, редактирующий существующую запись
     * о CardTransfer в БД.
     *
     * @param cardTransferDto - это объект DTO.
     **/

    void updateCardTransfer(CardTransferDto cardTransferDto);

    /**
     * Метод, удаляющий запись
     * о CardTransfer в БД.
     *
     * @param cardTransferId - это поиск AccountTransfer по id.
     **/

    void deleteCardTransferById(long cardTransferId);
}
