package com.bank.transfer.services.phoneTransfer;

import com.bank.transfer.dto.transfersDto.PhoneTransferDto;
import java.util.List;

/**
 * Интерфейс-сервис для работы с PhoneTransfer.
 * Предоставляет методы для создания, получения, обновления
 * и удаления записей PhoneTransfer.
 **/

public interface PhoneTransferService {

    /**
     * Метод, предоставляющий информацию
     * о всех записях PhoneTransfer из БД.
     *
     * @return возвращает информацию
     * о всех записях PhoneTransfer из БД в формате DTO.
     **/

    List<PhoneTransferDto> getAllPhoneTransfers();

    /**
     * Метод, предоставляющий информацию
     * об определённой записи PhoneTransfer из БД по id.
     *
     * @return возвращает информацию
     * о записи PhoneTransfer из БД по id в формате DTO.
     **/

    PhoneTransferDto getPhoneTransferById(Long phoneTransferId);

    /**
     * Метод, создающий новую запись
     * о PhoneTransfer в БД.
     *
     * @param phoneTransferDto - это объект DTO.
     **/

    void createPhoneTransfer(PhoneTransferDto phoneTransferDto);

    /**
     * Метод, редактирующий существующую запись
     * о PhoneTransfer в БД.
     *
     * @param phoneTransferDto - это объект DTO.
     **/

    void updatePhoneTransfer(PhoneTransferDto phoneTransferDto);

    /**
     * Метод, удаляющий запись
     * о PhoneTransfer в БД.
     *
     * @param phoneTransferId - это поиск AccountTransfer по id.
     **/

    void deletePhoneTransferById(long phoneTransferId);
}
