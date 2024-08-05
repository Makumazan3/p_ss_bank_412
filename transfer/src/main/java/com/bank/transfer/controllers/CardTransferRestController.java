package com.bank.transfer.controllers;

import com.bank.transfer.dto.transfersDto.CardTransferDto;
import com.bank.transfer.services.cardTransferService.CardTransferService;
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
 * предоставления информации связанной с CardTransfer.
 * Обрабатывает HTTP-запросы, связанные с операциями
 * создания, получения, обновления и удаления записей
 * об CardTransfer.
 **/

@RestController
@AllArgsConstructor
public class CardTransferRestController {

    private final CardTransferService cardTransferService;
    private final Logger logger = LoggerFactory.getLogger(CardTransferRestController.class);

    /**
     * Метод, предоставляющий информацию
     * о всех записях CardTransfer из БД.
     *
     * @return возвращает информацию
     * о всех записях CardTransfer из БД.
     **/

    @GetMapping("/card-transfer/getAll")
    public List<CardTransferDto> getAllCardTransfers() {
        logger.info("Старт контроллер-метода getAllCardTransfers");
        return cardTransferService.getAllCardTransfers();
    }

    /**
     * Метод, предоставляющий информацию
     * об определённой записи CardTransfer из БД по id.
     *
     * @return возвращает информацию
     * о записи CardTransfer из БД по id.
     **/

    @GetMapping("/card-transfer/get/{id}")
    public CardTransferDto getCardTransfer(@PathVariable long id) {
        logger.info("Старт контроллер-метода getCardTransfer");
        return cardTransferService.getCardTransferById(id);
    }

    /**
     * Метод, создающий новую запись
     * о CardTransfer в БД.
     *
     * @param cardTransferDto - это объект DTO.
     **/

    @PostMapping("/card-transfer/create")
    public void createCardTransfer(@Valid @RequestBody CardTransferDto cardTransferDto) {
        logger.info("Старт контроллер-метода createCardTransfer");
        cardTransferService.createCardTransfer(cardTransferDto);
    }

    /**
     * Метод, редактирующий существующую запись
     * о CardTransfer в БД.
     *
     * @param cardTransferDto - это объект DTO.
     **/

    @PatchMapping("/card-transfer/update")
    public void updateCardTransfer(@Valid @RequestBody CardTransferDto cardTransferDto) {
        logger.info("Старт контроллер-метода updateCardTransfer");
        cardTransferService.updateCardTransfer(cardTransferDto);
    }

    /**
     * Метод, удаляющий запись
     * о CardTransfer в БД.
     *
     * @param id - это поиск id CardTransfer.
     **/

    @DeleteMapping("/card-transfer/delete/{id}")
    public  void deleteCardTransfer(@PathVariable long id) {
        logger.info("Старт контроллер-метода deleteCardTransfer");
        cardTransferService.deleteCardTransferById(id);
    }
}
