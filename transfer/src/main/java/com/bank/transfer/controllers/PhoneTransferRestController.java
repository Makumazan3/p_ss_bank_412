package com.bank.transfer.controllers;

import com.bank.transfer.dto.transfersDto.PhoneTransferDto;
import com.bank.transfer.services.phoneTransfer.PhoneTransferServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.validation.Valid;
import java.util.List;

/**
 * Класс-контроллер для обработки ввода и
 * предоставления информации связанной с PhoneTransfer.
 * Обрабатывает HTTP-запросы, связанные с операциями
 * создания, получения, обновления и удаления записей
 * об AccountTransfer.
 **/

@RestController
@AllArgsConstructor
public class PhoneTransferRestController {

    private final PhoneTransferServiceImpl phoneTransferService;
    private final Logger logger = LoggerFactory.getLogger(PhoneTransferRestController.class);

    /**
     * Метод, предоставляющий информацию
     * о всех записях PhoneTransfer из БД.
     *
     * @return возвращает информацию
     * о всех записях PhoneTransfer из БД.
     **/

    @GetMapping("/phone-transfer/getAll")
    public List<PhoneTransferDto> getAllPhoneTransfers() {
        logger.info("Старт контроллер-метода getAllPhoneTransfers");
        return phoneTransferService.getAllPhoneTransfers();
    }

    /**
     * Метод, предоставляющий информацию
     * об определённой записи PhoneTransfer из БД по id.
     *
     * @return возвращает информацию
     * о записи PhoneTransfer из БД по id.
     **/

    @GetMapping("/phone-transfer/get/{id}")
    public PhoneTransferDto getPhoneTransfer(@PathVariable long id) {
        logger.info("Старт контроллер-метода getPhoneTransfer");
        return phoneTransferService.getPhoneTransferById(id);
    }

    /**
     * Метод, создающий новую запись
     * о PhoneTransfer в БД.
     *
     * @param phoneTransferDto - это объект DTO.
     **/

    @PostMapping("/phone-transfer/create")
    public void createPhoneTransfer(@Valid @RequestBody PhoneTransferDto phoneTransferDto) {
        logger.info("Старт контроллер-метода createPhoneTransfer");
        phoneTransferService.createPhoneTransfer(phoneTransferDto);
    }

    /**
     * Метод, редактирующий существующую запись
     * о PhoneTransfer в БД.
     *
     * @param phoneTransferDto - это объект DTO.
     **/

    @PatchMapping("/phone-transfer/update")
    public void updatePhoneTransfer(@Valid @RequestBody PhoneTransferDto phoneTransferDto) {
        logger.info("Старт контроллер-метода updatePhoneTransfer");
        phoneTransferService.updatePhoneTransfer(phoneTransferDto);
    }

    /**
     * Метод, удаляющий запись
     * о PhoneTransfer в БД.
     *
     * @param id - это поиск id PhoneTransfer.
     **/

    @DeleteMapping("/phone-transfer/delete/{id}")
    public void deletePhoneTransfer(@PathVariable long id) {
        logger.info("Старт контроллер-метода deletePhoneTransfer");
        phoneTransferService.deletePhoneTransferById(id);
    }
}

